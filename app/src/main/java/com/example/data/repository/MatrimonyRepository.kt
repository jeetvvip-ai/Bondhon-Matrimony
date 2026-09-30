package com.example.data.repository

import com.example.data.local.BondhonDao
import com.example.data.model.ChatMessage
import com.example.data.model.Conversation
import com.example.data.model.InterestRecord
import com.example.data.model.MatchCalculationResult
import com.example.data.model.MatrimonialProfile
import com.example.data.model.NotificationItem
import com.example.data.model.PartnerPreferences
import com.example.data.model.ProfileVisitor
import com.example.data.model.ShortlistRecord
import com.example.data.model.SuccessStory
import com.example.data.model.UserAccount
import com.example.data.model.UserReport
import kotlinx.coroutines.flow.Flow

class MatrimonyRepository(private val dao: BondhonDao) {

    val publishedProfiles: Flow<List<MatrimonialProfile>> = dao.getPublishedProfiles()
    val allProfiles: Flow<List<MatrimonialProfile>> = dao.getAllProfiles()
    val partnerPreferences: Flow<PartnerPreferences?> = dao.getPreferences("current_user")
    val interests: Flow<List<InterestRecord>> = dao.getInterests()
    val shortlists: Flow<List<ShortlistRecord>> = dao.getShortlists()
    val conversations: Flow<List<Conversation>> = dao.getConversations()
    val visitors: Flow<List<ProfileVisitor>> = dao.getVisitors()
    val notifications: Flow<List<NotificationItem>> = dao.getNotifications()
    val reports: Flow<List<UserReport>> = dao.getReports()

    // User Account & Authentication
    suspend fun registerUser(account: UserAccount): Boolean {
        val existing = dao.getUserByEmailOrMobile(account.email)
            ?: dao.getUserByEmailOrMobile(account.mobileNumber)
        if (existing != null) {
            return false // Already exists
        }
        dao.insertUserAccount(account)
        return true
    }

    suspend fun loginUser(identifier: String, passwordAttempt: String): UserAccount? {
        val account = dao.getUserByEmailOrMobile(identifier.trim()) ?: return null
        if (account.passwordHash == passwordAttempt) {
            return account
        }
        return null
    }

    suspend fun getUserByEmailOrMobile(identifier: String): UserAccount? {
        return dao.getUserByEmailOrMobile(identifier.trim())
    }

    suspend fun resetPassword(identifier: String, newPassword: String): Boolean {
        val rows = dao.updatePassword(identifier.trim(), newPassword)
        return rows > 0
    }

    suspend fun changeEmail(userId: String, newEmail: String) {
        dao.updateEmail(userId, newEmail.trim())
    }

    suspend fun changeMobile(userId: String, newMobile: String) {
        dao.updateMobile(userId, newMobile.trim())
    }

    suspend fun deleteUserAccount(userId: String) {
        dao.deleteUserAccount(userId)
        dao.deleteProfile(userId)
    }

    fun getProfile(id: String): Flow<MatrimonialProfile?> = dao.getProfileById(id)
    suspend fun getProfileOnce(id: String): MatrimonialProfile? = dao.getProfileByIdOnce(id)

    fun getMessages(conversationId: String): Flow<List<ChatMessage>> = dao.getMessages(conversationId)

    suspend fun deleteMessage(messageId: Long) {
        dao.deleteMessage(messageId)
    }

    suspend fun toggleShortlist(profileId: String, currentStatus: Boolean) {
        val newStatus = !currentStatus
        dao.updateShortlist(profileId, newStatus)
        if (newStatus) {
            dao.insertShortlist(ShortlistRecord(profileId = profileId, type = "MY_SHORTLIST"))
            dao.insertNotification(
                NotificationItem(
                    title = "Profile Shortlisted",
                    message = "You shortlisted profile #$profileId.",
                    type = "SHORTLIST",
                    targetProfileId = profileId
                )
            )
        } else {
            dao.deleteShortlist(profileId, "MY_SHORTLIST")
        }
    }

    suspend fun sendInterest(profile: MatrimonialProfile) {
        dao.updateInterestStatus(profile.id, "SENT")
        dao.insertInterest(
            InterestRecord(
                targetProfileId = profile.id,
                direction = "SENT",
                status = "SENT"
            )
        )
        dao.insertNotification(
            NotificationItem(
                title = "Interest Sent Successfully",
                message = "Your matrimonial interest was delivered to ${profile.fullName}.",
                type = "INTEREST",
                targetProfileId = profile.id
            )
        )
    }

    suspend fun acceptInterest(profile: MatrimonialProfile) {
        dao.updateInterestStatus(profile.id, "ACCEPTED")
        dao.updateInterestByProfileId(profile.id, "ACCEPTED")
        
        // Ensure conversation is created when connected
        dao.insertConversation(
            Conversation(
                id = profile.id,
                participantName = profile.fullName,
                participantProfession = profile.profession,
                participantCity = profile.city,
                participantAvatarSeed = profile.avatarSeed,
                participantBgColorHex = profile.avatarBgColorHex,
                lastMessage = "You are now connected! Start a warm conversation.",
                lastMessageTimestamp = System.currentTimeMillis(),
                unreadCount = 0,
                isOnline = true
            )
        )

        dao.insertNotification(
            NotificationItem(
                title = "Connected with ${profile.fullName}!",
                message = "Mutual connection established. You can now chat directly.",
                type = "ACCEPTED",
                targetProfileId = profile.id
            )
        )
    }

    suspend fun declineInterest(profileId: String) {
        dao.updateInterestStatus(profileId, "DECLINED")
        dao.updateInterestByProfileId(profileId, "DECLINED")
    }

    suspend fun sendMessage(conversationId: String, text: String, currentUserName: String = "You") {
        if (text.isBlank()) return
        val now = System.currentTimeMillis()
        dao.insertMessage(
            ChatMessage(
                conversationId = conversationId,
                senderId = "current_user",
                receiverId = conversationId,
                messageText = text.trim(),
                timestamp = now,
                isRead = true
            )
        )
        val profile = dao.getProfileByIdOnce(conversationId)
        if (profile != null) {
            dao.insertConversation(
                Conversation(
                    id = profile.id,
                    participantName = profile.fullName,
                    participantProfession = profile.profession,
                    participantCity = profile.city,
                    participantAvatarSeed = profile.avatarSeed,
                    participantBgColorHex = profile.avatarBgColorHex,
                    lastMessage = text.trim(),
                    lastMessageTimestamp = now,
                    unreadCount = 0,
                    isOnline = true
                )
            )
        }
    }

    suspend fun savePreferences(preferences: PartnerPreferences) {
        dao.savePreferences(preferences)
    }

    suspend fun saveProfile(profile: MatrimonialProfile) {
        dao.insertProfile(profile)
    }

    suspend fun recordProfileVisit(profile: MatrimonialProfile) {
        dao.insertVisitor(
            ProfileVisitor(
                profileId = profile.id,
                name = profile.fullName,
                age = profile.age,
                location = "${profile.city}, ${profile.state}",
                profession = profile.profession,
                avatarSeed = profile.avatarSeed,
                avatarBgColorHex = profile.avatarBgColorHex
            )
        )
    }

    suspend fun reportProfile(profileId: String, profileName: String, reason: String, details: String) {
        dao.insertReport(
            UserReport(
                reportedUserId = profileId,
                reportedUserName = profileName,
                reportedByUserId = "current_user",
                reportedByName = "Member",
                reason = reason,
                details = details,
                date = "Today",
                status = "Pending Review"
            )
        )
    }

    suspend fun blockProfile(profileId: String) {
        dao.updateAccountStatus(profileId, "Blocked")
    }

    suspend fun submitVerificationRequest(id: String = "current_user") {
        dao.updateVerificationStatus(id, "Verification Pending")
        dao.insertNotification(
            NotificationItem(
                title = "Verification Documents Submitted",
                message = "Your identity proof was received and is queued for manual admin review.",
                type = "VERIFICATION",
                targetProfileId = id
            )
        )
    }

    suspend fun adminVerifyProfile(profileId: String, isApproved: Boolean) {
        val status = if (isApproved) "Verified" else "Rejected"
        dao.updateVerificationStatus(profileId, status)
        dao.insertNotification(
            NotificationItem(
                title = if (isApproved) "Verification Approved ✓" else "Verification Request Update",
                message = if (isApproved) "Profile has been granted the official Verified Trust Badge by Admin." else "Verification documents were declined. Please contact support or re-submit.",
                type = "VERIFICATION",
                targetProfileId = profileId
            )
        )
    }

    suspend fun adminUpdateAccountStatus(profileId: String, status: String) {
        dao.updateAccountStatus(profileId, status)
    }

    suspend fun adminDeleteProfile(profileId: String) {
        dao.deleteProfile(profileId)
    }

    suspend fun adminUpdateReportStatus(reportId: Long, status: String) {
        dao.updateReportStatus(reportId, status)
    }

    suspend fun markAllNotificationsRead() {
        dao.markAllNotificationsRead()
    }

    fun calculateMatchScore(profile: MatrimonialProfile, preferences: PartnerPreferences?): MatchCalculationResult {
        var score = 65
        val tags = mutableListOf<String>()

        if (preferences != null) {
            // Age
            if (profile.age in preferences.preferredAgeMin..preferences.preferredAgeMax) {
                score += 10
                tags.add("Age Preference")
            }
            // Height
            if (profile.heightInches in preferences.preferredHeightMin..preferences.preferredHeightMax) {
                score += 5
                tags.add("Height Preference")
            }
            // Location
            if (preferences.preferredLocation == "Any" ||
                profile.city.contains(preferences.preferredLocation, ignoreCase = true) ||
                profile.state.contains(preferences.preferredLocation, ignoreCase = true)
            ) {
                score += 8
                tags.add("Location Preference")
            }
            // Religion
            if (preferences.preferredReligion == "Any" ||
                profile.religion.equals(preferences.preferredReligion, ignoreCase = true)
            ) {
                score += 8
                tags.add("Religion Preference")
            }
            // Community
            if (preferences.preferredCommunity == "Any" ||
                profile.community.contains(preferences.preferredCommunity, ignoreCase = true)
            ) {
                score += 5
                tags.add("Community Preference")
            }
            // Education
            if (preferences.preferredEducation == "Any" ||
                profile.highestQualification.contains(preferences.preferredEducation, ignoreCase = true)
            ) {
                score += 6
                tags.add("Education Preference")
            }
            // Lifestyle / Diet
            if (preferences.preferredDiet == "Any" ||
                profile.diet.equals(preferences.preferredDiet, ignoreCase = true)
            ) {
                score += 5
                tags.add("Lifestyle Preference")
            }
        } else {
            if (profile.age in 24..30) {
                score += 10
                tags.add("Age Compatible")
            }
            if (profile.highestQualification.contains("Tech", true) || profile.highestQualification.contains("M.", true)) {
                score += 8
                tags.add("Education Alignment")
            }
            if (profile.diet == "Vegetarian" || profile.diet == "Eggetarian") {
                score += 6
                tags.add("Diet Preference")
            }
        }

        if (tags.size < 4) {
            tags.add("Cultural Harmony")
        }

        val clamped = score.coerceIn(68, 96)
        return MatchCalculationResult(
            percentage = clamped,
            matchingTags = tags.take(4)
        )
    }

    fun getSuccessStories(): List<SuccessStory> {
        return listOf(
            SuccessStory(
                id = 1,
                brideName = "Pooja Banerjee",
                groomName = "Sourav Sen",
                weddingYear = "Married Nov 2025",
                location = "Kolkata & Bengaluru",
                quote = "“Bondhon helped us connect through shared cultural values and genuine family expectations. Today we are happily married.”",
                story = "Both working in demanding technology sectors in Bengaluru, Pooja and Sourav connected on Bondhon Matrimony after reviewing verified educational and family credentials. The zero-cost, family-first environment made exploring marriage natural and respectful."
            ),
            SuccessStory(
                id = 2,
                brideName = "Dr. Radhika Nair",
                groomName = "Vivek Menon",
                weddingYear = "Married Jan 2026",
                location = "Kochi & Mumbai",
                quote = "“The seriousness of profiles on Bondhon stood out immediately. It is truly a platform for marriage, not dating.”",
                story = "Radhika’s father created her profile emphasizing her devotion to medicine and classical arts. Vivek’s family reached out through the direct Interest system. Both families united with absolute blessing."
            ),
            SuccessStory(
                id = 3,
                brideName = "Meenakshi Sharma",
                groomName = "Abhishek Gupta",
                weddingYear = "Married Feb 2026",
                location = "Delhi NCR",
                quote = "“From the very first chat after our mutual interest was accepted, we knew our values and life goals matched completely.”",
                story = "Finding someone who appreciated simple lifestyle values in a fast-paced metropolis seemed difficult until Abhishek sent an interest on Bondhon. They spent 4 months getting to know each other with family involvement at every step."
            )
        )
    }
}
