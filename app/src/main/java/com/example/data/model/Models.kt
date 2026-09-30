package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_accounts")
data class UserAccount(
    @PrimaryKey val id: String, // e.g. "usr_101"
    val fullName: String,
    val email: String,
    val mobileNumber: String,
    val passwordHash: String,
    val gender: String, // "Female" or "Male"
    val dateOfBirth: String,
    val isTermsAccepted: Boolean = true,
    val isSuspended: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "profiles")
data class MatrimonialProfile(
    @PrimaryKey val id: String,
    val userId: String = id,
    val fullName: String,
    val gender: String, // "Female" or "Male"
    val age: Int,
    val dateOfBirth: String,
    val height: String,
    val heightInches: Int = 66,
    val religion: String,
    val community: String,
    val motherTongue: String,
    val maritalStatus: String,
    val city: String,
    val state: String,
    val country: String = "India",
    val highestQualification: String,
    val college: String,
    val fieldOfStudy: String = "",
    val profession: String,
    val company: String,
    val workLocation: String,
    val annualIncome: String,
    val fatherOccupation: String,
    val motherOccupation: String,
    val brothers: Int = 0,
    val sisters: Int = 0,
    val familyType: String = "Nuclear", // "Nuclear", "Joint"
    val familyValues: String = "Moderate", // "Traditional", "Moderate", "Liberal"
    val familyLocation: String = "",
    val diet: String = "Vegetarian", // "Vegetarian", "Non-Vegetarian", "Eggetarian", "Jain"
    val smoking: String = "No",
    val drinking: String = "No",
    val exercise: String = "Regular",
    val hobbies: String = "Reading, Music, Travel",
    val interests: String = "Culture, Heritage, Road Trips",
    val languagesKnown: String = "English, Hindi",
    val aboutMe: String = "",
    val avatarSeed: String = "",
    val avatarBgColorHex: Long = 0xFF7A1228,
    val photoPrivacy: String = "Public", // "Public" (to registered users), "Private"
    val verificationStatus: String = "Not Verified", // "Not Verified", "Verification Pending", "Verified", "Rejected"
    val isShortlisted: Boolean = false,
    val interestStatus: String = "NONE", // "NONE", "SENT", "RECEIVED", "ACCEPTED", "DECLINED"
    val isBlocked: Boolean = false,
    val isReported: Boolean = false,
    val accountStatus: String = "Active", // "Active", "Pending", "Suspended", "Blocked"
    val isPublished: Boolean = true, // Must be true to appear in public search
    val createdFor: String = "Myself",
    val lastActive: String = "Online now",
    val additionalPhotosCount: Int = 3
)

@Entity(tableName = "partner_preferences")
data class PartnerPreferences(
    @PrimaryKey val id: String = "current_user",
    val preferredAgeMin: Int = 21,
    val preferredAgeMax: Int = 35,
    val preferredHeightMin: Int = 58,
    val preferredHeightMax: Int = 76,
    val preferredLocation: String = "Any",
    val preferredReligion: String = "Any",
    val preferredCommunity: String = "Any",
    val preferredMotherTongue: String = "Any",
    val preferredEducation: String = "Any",
    val preferredProfession: String = "Any",
    val preferredIncome: String = "Any",
    val preferredMaritalStatus: String = "Any",
    val preferredDiet: String = "Any",
    val preferredLifestyle: String = "Any"
)

@Entity(tableName = "interests")
data class InterestRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetProfileId: String,
    val direction: String, // "SENT" or "RECEIVED"
    val status: String, // "SENT", "ACCEPTED", "DECLINED"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "shortlists")
data class ShortlistRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: String,
    val type: String, // "MY_SHORTLIST" or "SHORTLISTED_ME"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val conversationId: String,
    val senderId: String,
    val receiverId: String,
    val messageText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = true
)

@Entity(tableName = "conversations")
data class Conversation(
    @PrimaryKey val id: String, // targetProfileId
    val participantName: String,
    val participantProfession: String,
    val participantCity: String,
    val participantAvatarSeed: String,
    val participantBgColorHex: Long,
    val lastMessage: String,
    val lastMessageTimestamp: Long,
    val unreadCount: Int = 0,
    val isOnline: Boolean = true
)

@Entity(tableName = "profile_visitors")
data class ProfileVisitor(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: String,
    val name: String,
    val age: Int,
    val location: String,
    val profession: String,
    val avatarSeed: String,
    val avatarBgColorHex: Long,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "notifications")
data class NotificationItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val type: String, // "INTEREST", "ACCEPTED", "MESSAGE", "MATCH", "SHORTLIST", "VERIFICATION", "WARNING"
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false,
    val targetProfileId: String = ""
)

@Entity(tableName = "reports")
data class UserReport(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val reportedUserId: String,
    val reportedUserName: String,
    val reportedByUserId: String = "current_user",
    val reportedByName: String = "Member",
    val reason: String,
    val details: String = "",
    val date: String,
    val status: String = "Pending Review" // "Pending Review", "Warned", "Suspended", "Dismissed"
)

data class SuccessStory(
    val id: Long,
    val brideName: String,
    val groomName: String,
    val weddingYear: String,
    val location: String,
    val quote: String,
    val story: String
)

data class MatchCalculationResult(
    val percentage: Int,
    val matchingTags: List<String>,
    val disclaimer: String = "Match percentage is only a platform-generated compatibility indicator based on your profile and partner preferences. It does not predict marriage or relationship success."
)
