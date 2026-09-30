package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BondhonDatabase
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
import com.example.data.repository.MatrimonyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    LANDING,
    LOGIN,
    REGISTER,
    FORGOT_PASSWORD,
    DASHBOARD,
    FIND_MATCHES,
    INTERESTS,
    SHORTLIST,
    MESSAGES,
    CHAT_DETAIL,
    PROFILE_DETAIL,
    PROFILE_WIZARD,
    PROFILE_VISITORS,
    SAFETY_PRIVACY,
    SETTINGS,
    VERIFICATION,
    SUCCESS_STORIES,
    ABOUT_FAQ_CONTACT,
    ADMIN_PORTAL,
    FIREBASE_GUIDE
}

data class FilterCriteria(
    val query: String = "",
    val gender: String = "All", // "All", "Bride" (Female), "Groom" (Male)
    val minAge: Int = 21,
    val maxAge: Int = 40,
    val religion: String = "All",
    val community: String = "All",
    val location: String = "All",
    val motherTongue: String = "All",
    val education: String = "All",
    val profession: String = "All",
    val income: String = "All",
    val maritalStatus: String = "All",
    val diet: String = "All",
    val isVerifiedOnly: Boolean = false,
    val isGridView: Boolean = true
)

class MatrimonyViewModel(application: Application) : AndroidViewModel(application) {

    private val database = BondhonDatabase.getDatabase(application, viewModelScope)
    val repository = MatrimonyRepository(database.dao())

    // Navigation state
    private val _currentScreen = MutableStateFlow(Screen.LANDING)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<Screen>()

    // Selected profile for Detail screen
    private val _selectedProfile = MutableStateFlow<MatrimonialProfile?>(null)
    val selectedProfile: StateFlow<MatrimonialProfile?> = _selectedProfile.asStateFlow()

    // Selected conversation for Chat Detail
    private val _selectedConversation = MutableStateFlow<Conversation?>(null)
    val selectedConversation: StateFlow<Conversation?> = _selectedConversation.asStateFlow()

    // Filters for Find Matches
    private val _filters = MutableStateFlow(FilterCriteria())
    val filters: StateFlow<FilterCriteria> = _filters.asStateFlow()

    // User session
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _currentAccount = MutableStateFlow<UserAccount?>(null)
    val currentAccount: StateFlow<UserAccount?> = _currentAccount.asStateFlow()

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin.asStateFlow()

    // SnackBar message
    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> = _snackBarMessage.asStateFlow()

    // Logged in user matrimonial profile
    val myProfile = MutableStateFlow(
        MatrimonialProfile(
            id = "current_user",
            userId = "current_user",
            fullName = "Amitav Sen",
            gender = "Male",
            age = 28,
            dateOfBirth = "12 Jul 1998",
            height = "5 ft 10 in (178 cm)",
            heightInches = 70,
            religion = "Hindu",
            community = "Kayastha",
            motherTongue = "Bengali",
            maritalStatus = "Never Married",
            city = "Kolkata",
            state = "West Bengal",
            highestQualification = "B.Tech & M.S. in Computer Science",
            college = "Jadavpur University",
            fieldOfStudy = "Computer Engineering & Cloud Systems",
            profession = "Staff Software Architect",
            company = "Enterprise Cloud Platforms",
            workLocation = "Kolkata & Remote",
            annualIncome = "₹20–30 Lakh",
            fatherOccupation = "Senior Professor of Economics (Retd)",
            motherOccupation = "Homemaker & Writer",
            brothers = 1,
            sisters = 0,
            familyType = "Nuclear",
            familyValues = "Moderate",
            familyLocation = "Salt Lake, Kolkata",
            diet = "Non-Vegetarian",
            smoking = "No",
            drinking = "No",
            exercise = "Regular Swimming & Cycling",
            hobbies = "Reading Classic Literature, Photography, Road Trips, Cooking",
            interests = "Literature, Cloud Architecture, Heritage Travel",
            languagesKnown = "Bengali, English, Hindi",
            aboutMe = "A grounded, forward-looking person who values cultural heritage and close-knit family relationships. Looking for an understanding partner who shares an open mindset and a passion for lifelong growth.",
            avatarSeed = "AS",
            avatarBgColorHex = 0xFF7A1228,
            verificationStatus = "Verified",
            isPublished = true
        )
    )

    // Data streams from repository
    val publishedProfiles: StateFlow<List<MatrimonialProfile>> = repository.publishedProfiles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProfiles: StateFlow<List<MatrimonialProfile>> = repository.allProfiles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val partnerPreferences: StateFlow<PartnerPreferences?> = repository.partnerPreferences
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val interests: StateFlow<List<InterestRecord>> = repository.interests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val shortlists: StateFlow<List<ShortlistRecord>> = repository.shortlists
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val conversations: StateFlow<List<Conversation>> = repository.conversations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val visitors: StateFlow<List<ProfileVisitor>> = repository.visitors
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val reports: StateFlow<List<UserReport>> = repository.reports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered profiles for Find Matches (only published profiles appear)
    val filteredProfiles: StateFlow<List<MatrimonialProfile>> = combine(publishedProfiles, filters) { list, criteria ->
        list.filter { p ->
            val matchesQuery = criteria.query.isBlank() ||
                    p.fullName.contains(criteria.query, ignoreCase = true) ||
                    p.city.contains(criteria.query, ignoreCase = true) ||
                    p.profession.contains(criteria.query, ignoreCase = true) ||
                    p.religion.contains(criteria.query, ignoreCase = true) ||
                    p.community.contains(criteria.query, ignoreCase = true)

            val matchesGender = when (criteria.gender) {
                "Bride" -> p.gender == "Female"
                "Groom" -> p.gender == "Male"
                else -> true
            }

            val matchesAge = p.age in criteria.minAge..criteria.maxAge

            val matchesReligion = criteria.religion == "All" || p.religion.equals(criteria.religion, ignoreCase = true)
            val matchesCommunity = criteria.community == "All" || p.community.contains(criteria.community, ignoreCase = true)
            val matchesLocation = criteria.location == "All" || p.city.contains(criteria.location, ignoreCase = true) || p.state.contains(criteria.location, ignoreCase = true)
            val matchesTongue = criteria.motherTongue == "All" || p.motherTongue.contains(criteria.motherTongue, ignoreCase = true)
            val matchesEducation = criteria.education.isEmpty() || criteria.education == "All" || p.highestQualification.contains(criteria.education, ignoreCase = true)
            val matchesProfession = criteria.profession == "All" || p.profession.contains(criteria.profession, ignoreCase = true)
            val matchesIncome = criteria.income == "All" || p.annualIncome.contains(criteria.income, ignoreCase = true)
            val matchesMarital = criteria.maritalStatus == "All" || p.maritalStatus.equals(criteria.maritalStatus, ignoreCase = true)
            val matchesDiet = criteria.diet == "All" || p.diet.equals(criteria.diet, ignoreCase = true)
            val matchesVerified = !criteria.isVerifiedOnly || p.verificationStatus == "Verified"

            matchesQuery && matchesGender && matchesAge && matchesReligion && matchesCommunity &&
                    matchesLocation && matchesTongue && matchesEducation && matchesProfession &&
                    matchesIncome && matchesMarital && matchesDiet && matchesVerified
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Navigation actions
    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenBackStack.isNotEmpty()) {
            val previous = screenBackStack.removeAt(screenBackStack.size - 1)
            _currentScreen.value = previous
            return true
        }
        return false
    }

    fun openProfileDetail(profile: MatrimonialProfile) {
        if (!_isLoggedIn.value) {
            _snackBarMessage.value = "Please sign in to view complete matrimonial profiles."
            navigateTo(Screen.LOGIN)
            return
        }
        _selectedProfile.value = profile
        viewModelScope.launch {
            repository.recordProfileVisit(profile)
        }
        navigateTo(Screen.PROFILE_DETAIL)
    }

    fun openChatDetail(conversation: Conversation) {
        _selectedConversation.value = conversation
        navigateTo(Screen.CHAT_DETAIL)
    }

    fun openChatWithProfile(profile: MatrimonialProfile) {
        val conv = Conversation(
            id = profile.id,
            participantName = profile.fullName,
            participantProfession = profile.profession,
            participantCity = profile.city,
            participantAvatarSeed = profile.avatarSeed,
            participantBgColorHex = profile.avatarBgColorHex,
            lastMessage = "You are now connected on Bondhon!",
            lastMessageTimestamp = System.currentTimeMillis(),
            unreadCount = 0,
            isOnline = true
        )
        _selectedConversation.value = conv
        navigateTo(Screen.CHAT_DETAIL)
    }

    // Filter mutations
    fun updateFilters(update: FilterCriteria) {
        _filters.value = update
    }

    fun resetFilters() {
        _filters.value = FilterCriteria()
    }

    // Authentication actions
    fun login(identifier: String, passwordAttempt: String, rememberMe: Boolean, onResult: (Boolean, String?) -> Unit) {
        if (identifier.isBlank()) {
            onResult(false, "Please enter your email or mobile number.")
            return
        }
        if (passwordAttempt.isBlank()) {
            onResult(false, "Please enter your password.")
            return
        }

        viewModelScope.launch {
            val account = repository.loginUser(identifier, passwordAttempt)
            if (account == null) {
                // Check if account exists with different password or not at all
                val userExists = repository.getUserByEmailOrMobile(identifier)
                if (userExists != null) {
                    if (userExists.isSuspended) {
                        onResult(false, "Your account has been suspended. Please contact support.")
                    } else {
                        onResult(false, "Incorrect password. Please try again.")
                    }
                } else {
                    onResult(false, "Account not found with this email or mobile number.")
                }
            } else {
                if (account.isSuspended) {
                    onResult(false, "Your account has been suspended.")
                    return@launch
                }
                _currentAccount.value = account
                _isLoggedIn.value = true

                // Sync profile data
                val existingProfile = repository.getProfileOnce(account.id)
                if (existingProfile != null) {
                    myProfile.value = existingProfile
                    if (!existingProfile.isPublished) {
                        _currentScreen.value = Screen.PROFILE_WIZARD
                    } else {
                        _currentScreen.value = Screen.DASHBOARD
                    }
                } else {
                    // Incomplete profile -> redirect to Complete Your Matrimonial Profile
                    myProfile.value = myProfile.value.copy(
                        id = account.id,
                        userId = account.id,
                        fullName = account.fullName,
                        gender = account.gender,
                        dateOfBirth = account.dateOfBirth,
                        isPublished = false
                    )
                    _currentScreen.value = Screen.PROFILE_WIZARD
                }
                onResult(true, null)
            }
        }
    }

    fun register(
        fullName: String,
        email: String,
        mobileNumber: String,
        password: String,
        confirmPassword: String,
        gender: String,
        dateOfBirth: String,
        isTermsAccepted: Boolean,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (fullName.isBlank()) {
            onResult(false, "Please enter your full name.")
            return
        }
        if (email.isBlank() || !email.contains("@")) {
            onResult(false, "Please enter a valid email address.")
            return
        }
        if (mobileNumber.isBlank() || mobileNumber.length < 10) {
            onResult(false, "Please enter a valid 10-digit mobile number.")
            return
        }
        if (password.length < 6) {
            onResult(false, "Password must be at least 6 characters long.")
            return
        }
        if (password != confirmPassword) {
            onResult(false, "Passwords do not match.")
            return
        }
        if (!isTermsAccepted) {
            onResult(false, "You must agree to the Terms of Service and Privacy Policy.")
            return
        }

        val newId = "usr_" + System.currentTimeMillis().toString().takeLast(6)
        val account = UserAccount(
            id = newId,
            fullName = fullName.trim(),
            email = email.trim(),
            mobileNumber = mobileNumber.trim(),
            passwordHash = password,
            gender = gender,
            dateOfBirth = dateOfBirth,
            isTermsAccepted = true
        )

        viewModelScope.launch {
            val registered = repository.registerUser(account)
            if (!registered) {
                onResult(false, "An account with this email or mobile number already exists.")
            } else {
                _currentAccount.value = account
                _isLoggedIn.value = true

                // Create initial unpublished profile & direct to Profile Wizard
                val initialProfile = MatrimonialProfile(
                    id = newId,
                    userId = newId,
                    fullName = fullName.trim(),
                    gender = gender,
                    age = 26,
                    dateOfBirth = dateOfBirth,
                    height = "5 ft 8 in (172 cm)",
                    heightInches = 68,
                    religion = "Hindu",
                    community = "",
                    motherTongue = "",
                    maritalStatus = "Never Married",
                    city = "",
                    state = "",
                    highestQualification = "",
                    college = "",
                    fieldOfStudy = "",
                    profession = "",
                    company = "",
                    workLocation = "",
                    annualIncome = "Not Specified",
                    fatherOccupation = "",
                    motherOccupation = "",
                    aboutMe = "",
                    avatarSeed = fullName.trim().split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString(""),
                    avatarBgColorHex = 0xFF7A1228,
                    isPublished = false,
                    verificationStatus = "Not Verified"
                )
                myProfile.value = initialProfile
                repository.saveProfile(initialProfile)

                _currentScreen.value = Screen.PROFILE_WIZARD
                onResult(true, null)
            }
        }
    }

    fun requestPasswordReset(identifier: String, onResult: (Boolean, String) -> Unit) {
        if (identifier.isBlank()) {
            onResult(false, "Please enter your registered email or mobile number.")
            return
        }
        viewModelScope.launch {
            val user = repository.getUserByEmailOrMobile(identifier)
            if (user != null) {
                // In production with Firebase Auth, sendPasswordResetEmail() is dispatched.
                // In local prototype, we confirm dispatch and update temporary passcode.
                repository.resetPassword(identifier, "BondhonReset123")
                onResult(true, "A secure password reset link and temporary passkey (BondhonReset123) has been generated for ${user.email}.")
            } else {
                onResult(false, "No registered account found with that email or mobile number.")
            }
        }
    }

    fun changePassword(oldPass: String, newPass: String, onResult: (Boolean, String) -> Unit) {
        val user = _currentAccount.value
        if (user == null) {
            onResult(false, "Please log in first.")
            return
        }
        if (user.passwordHash != oldPass) {
            onResult(false, "Current password is incorrect.")
            return
        }
        if (newPass.length < 6) {
            onResult(false, "New password must be at least 6 characters.")
            return
        }

        viewModelScope.launch {
            repository.resetPassword(user.email, newPass)
            _currentAccount.value = user.copy(passwordHash = newPass)
            onResult(true, "Password changed successfully.")
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _currentAccount.value = null
        _isAdmin.value = false
        _currentScreen.value = Screen.LANDING
        _snackBarMessage.value = "You have been logged out."
    }

    // Shortlist
    fun toggleShortlist(profile: MatrimonialProfile) {
        if (!_isLoggedIn.value) {
            _snackBarMessage.value = "Please log in to shortlist profiles."
            navigateTo(Screen.LOGIN)
            return
        }
        viewModelScope.launch {
            repository.toggleShortlist(profile.id, profile.isShortlisted)
            val msg = if (!profile.isShortlisted) "Added ${profile.fullName} to your Shortlist" else "Removed from Shortlist"
            _snackBarMessage.value = msg
        }
    }

    // Interest
    fun sendInterest(profile: MatrimonialProfile) {
        if (!_isLoggedIn.value) {
            _snackBarMessage.value = "Please log in to send matrimonial interests."
            navigateTo(Screen.LOGIN)
            return
        }
        viewModelScope.launch {
            repository.sendInterest(profile)
            _snackBarMessage.value = "Interest Sent Successfully to ${profile.fullName}!"
        }
    }

    fun acceptInterest(profile: MatrimonialProfile) {
        viewModelScope.launch {
            repository.acceptInterest(profile)
            _snackBarMessage.value = "You are now connected with ${profile.fullName}!"
        }
    }

    fun declineInterest(profileId: String) {
        viewModelScope.launch {
            repository.declineInterest(profileId)
            _snackBarMessage.value = "Interest declined."
        }
    }

    fun maybeLaterInterest(profileId: String) {
        viewModelScope.launch {
            _snackBarMessage.value = "Marked for later consideration."
        }
    }

    // Messaging
    fun sendMessage(conversationId: String, text: String) {
        viewModelScope.launch {
            repository.sendMessage(conversationId, text, myProfile.value.fullName)
        }
    }

    fun deleteOwnMessage(messageId: Long) {
        viewModelScope.launch {
            repository.deleteMessage(messageId)
            _snackBarMessage.value = "Message deleted."
        }
    }

    // Preferences & Profile update
    fun savePreferences(preferences: PartnerPreferences) {
        viewModelScope.launch {
            repository.savePreferences(preferences)
            _snackBarMessage.value = "Partner preferences saved successfully!"
        }
    }

    fun saveMyProfile(profile: MatrimonialProfile) {
        myProfile.value = profile
        viewModelScope.launch {
            repository.saveProfile(profile)
            _snackBarMessage.value = "Profile updated successfully! Your profile is live on Bondhon."
        }
    }

    // Reporting & Blocking
    fun reportProfile(profileId: String, profileName: String, reason: String, details: String) {
        viewModelScope.launch {
            repository.reportProfile(profileId, profileName, reason, details)
            _snackBarMessage.value = "Profile reported. Our trust and safety team will review within 24 hours."
        }
    }

    fun blockProfile(profileId: String, profileName: String) {
        viewModelScope.launch {
            repository.blockProfile(profileId)
            _snackBarMessage.value = "$profileName has been blocked."
            navigateBack()
        }
    }

    // Verification
    fun requestVerification() {
        viewModelScope.launch {
            repository.submitVerificationRequest(myProfile.value.id)
            myProfile.value = myProfile.value.copy(verificationStatus = "Verification Pending")
            _snackBarMessage.value = "Documents submitted! Verification is pending manual admin review."
        }
    }

    // Admin operations
    fun adminApproveVerification(profileId: String) {
        viewModelScope.launch {
            repository.adminVerifyProfile(profileId, true)
            _snackBarMessage.value = "Verification approved for $profileId"
        }
    }

    fun adminRejectVerification(profileId: String) {
        viewModelScope.launch {
            repository.adminVerifyProfile(profileId, false)
            _snackBarMessage.value = "Verification rejected for $profileId"
        }
    }

    fun adminUpdateAccountStatus(profileId: String, status: String) {
        viewModelScope.launch {
            repository.adminUpdateAccountStatus(profileId, status)
            _snackBarMessage.value = "Status updated to $status for $profileId"
        }
    }

    fun adminDeleteProfile(profileId: String) {
        viewModelScope.launch {
            repository.adminDeleteProfile(profileId)
            _snackBarMessage.value = "Profile $profileId deleted."
        }
    }

    fun adminUpdateReportStatus(reportId: Long, status: String) {
        viewModelScope.launch {
            repository.adminUpdateReportStatus(reportId, status)
            _snackBarMessage.value = "Report #$reportId marked as $status"
        }
    }

    fun toggleAdminMode(enable: Boolean) {
        _isAdmin.value = enable
        if (enable) {
            _snackBarMessage.value = "Admin mode activated"
        }
    }

    fun clearSnackBar() {
        _snackBarMessage.value = null
    }

    fun calculateMatch(profile: MatrimonialProfile): MatchCalculationResult {
        return repository.calculateMatchScore(profile, partnerPreferences.value)
    }

    fun getSuccessStories(): List<SuccessStory> = repository.getSuccessStories()
}
