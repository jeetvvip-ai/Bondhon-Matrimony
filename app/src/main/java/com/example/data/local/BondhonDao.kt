package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ChatMessage
import com.example.data.model.Conversation
import com.example.data.model.InterestRecord
import com.example.data.model.MatrimonialProfile
import com.example.data.model.NotificationItem
import com.example.data.model.PartnerPreferences
import com.example.data.model.ProfileVisitor
import com.example.data.model.ShortlistRecord
import com.example.data.model.UserAccount
import com.example.data.model.UserReport
import kotlinx.coroutines.flow.Flow

@Dao
interface BondhonDao {

    // User Accounts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAccount(account: UserAccount)

    @Query("SELECT * FROM user_accounts WHERE email = :identifier OR mobileNumber = :identifier LIMIT 1")
    suspend fun getUserByEmailOrMobile(identifier: String): UserAccount?

    @Query("SELECT * FROM user_accounts WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): UserAccount?

    @Query("UPDATE user_accounts SET passwordHash = :newPasswordHash WHERE email = :identifier OR mobileNumber = :identifier")
    suspend fun updatePassword(identifier: String, newPasswordHash: String): Int

    @Query("UPDATE user_accounts SET email = :newEmail WHERE id = :userId")
    suspend fun updateEmail(userId: String, newEmail: String)

    @Query("UPDATE user_accounts SET mobileNumber = :newMobile WHERE id = :userId")
    suspend fun updateMobile(userId: String, newMobile: String)

    @Query("DELETE FROM user_accounts WHERE id = :userId")
    suspend fun deleteUserAccount(userId: String)

    // Profiles
    @Query("SELECT * FROM profiles WHERE isPublished = 1 ORDER BY age ASC")
    fun getPublishedProfiles(): Flow<List<MatrimonialProfile>>

    @Query("SELECT * FROM profiles ORDER BY age ASC")
    fun getAllProfiles(): Flow<List<MatrimonialProfile>>

    @Query("SELECT * FROM profiles WHERE id = :id LIMIT 1")
    fun getProfileById(id: String): Flow<MatrimonialProfile?>

    @Query("SELECT * FROM profiles WHERE id = :id LIMIT 1")
    suspend fun getProfileByIdOnce(id: String): MatrimonialProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfiles(profiles: List<MatrimonialProfile>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: MatrimonialProfile)

    @Update
    suspend fun updateProfile(profile: MatrimonialProfile)

    @Query("UPDATE profiles SET isShortlisted = :isShortlisted WHERE id = :id")
    suspend fun updateShortlist(id: String, isShortlisted: Boolean)

    @Query("UPDATE profiles SET interestStatus = :status WHERE id = :id")
    suspend fun updateInterestStatus(id: String, status: String)

    @Query("UPDATE profiles SET verificationStatus = :status WHERE id = :id")
    suspend fun updateVerificationStatus(id: String, status: String)

    @Query("UPDATE profiles SET accountStatus = :status WHERE id = :id")
    suspend fun updateAccountStatus(id: String, status: String)

    @Query("UPDATE profiles SET isPublished = :isPublished WHERE id = :id")
    suspend fun updatePublishStatus(id: String, isPublished: Boolean)

    @Query("DELETE FROM profiles WHERE id = :id")
    suspend fun deleteProfile(id: String)

    // Partner Preferences
    @Query("SELECT * FROM partner_preferences WHERE id = :id LIMIT 1")
    fun getPreferences(id: String = "current_user"): Flow<PartnerPreferences?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePreferences(preferences: PartnerPreferences)

    // Interests
    @Query("SELECT * FROM interests ORDER BY timestamp DESC")
    fun getInterests(): Flow<List<InterestRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterest(record: InterestRecord)

    @Query("UPDATE interests SET status = :status WHERE targetProfileId = :profileId")
    suspend fun updateInterestByProfileId(profileId: String, status: String)

    // Shortlists
    @Query("SELECT * FROM shortlists ORDER BY timestamp DESC")
    fun getShortlists(): Flow<List<ShortlistRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortlist(record: ShortlistRecord)

    @Query("DELETE FROM shortlists WHERE profileId = :profileId AND type = :type")
    suspend fun deleteShortlist(profileId: String, type: String)

    // Conversations & Messages
    @Query("SELECT * FROM conversations ORDER BY lastMessageTimestamp DESC")
    fun getConversations(): Flow<List<Conversation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: Conversation)

    @Query("SELECT * FROM chat_messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun getMessages(conversationId: String): Flow<List<ChatMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessage)

    @Query("DELETE FROM chat_messages WHERE id = :messageId")
    suspend fun deleteMessage(messageId: Long)

    // Visitors
    @Query("SELECT * FROM profile_visitors ORDER BY timestamp DESC")
    fun getVisitors(): Flow<List<ProfileVisitor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisitor(visitor: ProfileVisitor)

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getNotifications(): Flow<List<NotificationItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItem)

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :id")
    suspend fun markNotificationRead(id: Long)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllNotificationsRead()

    // Reports
    @Query("SELECT * FROM reports ORDER BY id DESC")
    fun getReports(): Flow<List<UserReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: UserReport)

    @Query("UPDATE reports SET status = :status WHERE id = :id")
    suspend fun updateReportStatus(id: Long, status: String)
}
