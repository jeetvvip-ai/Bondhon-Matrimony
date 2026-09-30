package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserAccount::class,
        MatrimonialProfile::class,
        PartnerPreferences::class,
        InterestRecord::class,
        ShortlistRecord::class,
        ChatMessage::class,
        Conversation::class,
        ProfileVisitor::class,
        NotificationItem::class,
        UserReport::class
    ],
    version = 2,
    exportSchema = false
)
abstract class BondhonDatabase : RoomDatabase() {

    abstract fun dao(): BondhonDao

    companion object {
        @Volatile
        private var INSTANCE: BondhonDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BondhonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BondhonDatabase::class.java,
                    "bondhon_matrimony_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateInitialData(database.dao())
                }
            }
        }

        private suspend fun populateInitialData(dao: BondhonDao) {
            // Seed Demo User Accounts
            dao.insertUserAccount(
                UserAccount(
                    id = "current_user",
                    fullName = "Amitav Sen",
                    email = "amitav.sen@example.com",
                    mobileNumber = "+919876543210",
                    passwordHash = "password123",
                    gender = "Male",
                    dateOfBirth = "12 Jul 1998"
                )
            )
            dao.insertUserAccount(
                UserAccount(
                    id = "BM101",
                    fullName = "Ananya Chatterjee",
                    email = "ananya.c@example.com",
                    mobileNumber = "+919876543211",
                    passwordHash = "password123",
                    gender = "Female",
                    dateOfBirth = "14 Oct 1999"
                )
            )

            // Seed Profiles
            val sampleProfiles = listOf(
                MatrimonialProfile(
                    id = "BM101",
                    userId = "BM101",
                    fullName = "Ananya Chatterjee",
                    gender = "Female",
                    age = 26,
                    dateOfBirth = "14 Oct 1999",
                    height = "5 ft 4 in (163 cm)",
                    heightInches = 64,
                    religion = "Hindu",
                    community = "Brahmin",
                    motherTongue = "Bengali",
                    maritalStatus = "Never Married",
                    city = "Kolkata",
                    state = "West Bengal",
                    highestQualification = "M.Tech in Computer Science",
                    college = "Jadavpur University",
                    fieldOfStudy = "Computer Science & Artificial Intelligence",
                    profession = "Senior Software Engineer",
                    company = "Leading Cloud Tech Firm",
                    workLocation = "Salt Lake Sector V, Kolkata",
                    annualIncome = "₹20–30 Lakh",
                    fatherOccupation = "Senior Central Govt Officer (Retd)",
                    motherOccupation = "High School Teacher",
                    brothers = 1,
                    sisters = 0,
                    familyType = "Nuclear",
                    familyValues = "Moderate",
                    familyLocation = "Ballygunge, South Kolkata",
                    diet = "Non-Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Yoga & Morning Walks",
                    hobbies = "Classical Hindustani Vocals, Rabindra Sangeet, Baking, Travel",
                    interests = "Indian Classical Music, Literature, Cooking",
                    languagesKnown = "Bengali, English, Hindi",
                    aboutMe = "Warm, independent and family-rooted individual. I believe marriage is a lifelong companionship grounded in mutual respect, laughter, and cultural alignment. Looking for an educated partner who cherishes both modern ambitions and warm family traditions.",
                    avatarSeed = "AC",
                    avatarBgColorHex = 0xFF7A1228,
                    verificationStatus = "Verified",
                    interestStatus = "NONE",
                    isShortlisted = true,
                    isPublished = true,
                    lastActive = "Active 10 mins ago"
                ),
                MatrimonialProfile(
                    id = "BM102",
                    userId = "BM102",
                    fullName = "Dr. Rohan Malhotra",
                    gender = "Male",
                    age = 29,
                    dateOfBirth = "22 Aug 1996",
                    height = "5 ft 11 in (180 cm)",
                    heightInches = 71,
                    religion = "Hindu",
                    community = "Khatri",
                    motherTongue = "Hindi",
                    maritalStatus = "Never Married",
                    city = "Delhi",
                    state = "Delhi NCR",
                    highestQualification = "MBBS, MD (Cardiology)",
                    college = "All India Institute of Medical Sciences (AIIMS)",
                    fieldOfStudy = "Cardiovascular Medicine",
                    profession = "Cardiologist",
                    company = "Fortis Escorts Heart Institute",
                    workLocation = "Okhla, New Delhi",
                    annualIncome = "₹30 Lakh+",
                    fatherOccupation = "Businessman (Automotive Exports)",
                    motherOccupation = "Homemaker",
                    brothers = 1,
                    sisters = 1,
                    familyType = "Joint",
                    familyValues = "Moderate",
                    familyLocation = "South Extension, New Delhi",
                    diet = "Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Gym & Swimming",
                    hobbies = "Tennis, Reading Medical Journals, Road Trips, Acoustic Guitar",
                    interests = "Fitness, Medical Technology, Tennis",
                    languagesKnown = "Hindi, English, Punjabi",
                    aboutMe = "Dedicated medical doctor with a passion for clinical research and patient care. Raised with progressive Punjabi values and profound family bonding. Seeking a kind-hearted, intellectually curious life partner.",
                    avatarSeed = "RM",
                    avatarBgColorHex = 0xFF194D66,
                    verificationStatus = "Verified",
                    interestStatus = "ACCEPTED",
                    isShortlisted = false,
                    isPublished = true,
                    lastActive = "Online now"
                ),
                MatrimonialProfile(
                    id = "BM103",
                    userId = "BM103",
                    fullName = "Priyanka Deshmukh",
                    gender = "Female",
                    age = 27,
                    dateOfBirth = "05 Dec 1998",
                    height = "5 ft 5 in (165 cm)",
                    heightInches = 65,
                    religion = "Hindu",
                    community = "Maratha",
                    motherTongue = "Marathi",
                    maritalStatus = "Never Married",
                    city = "Mumbai",
                    state = "Maharashtra",
                    highestQualification = "Chartered Accountant (CA) & MBA Finance",
                    college = "Narsee Monjee Institute (NMIMS)",
                    fieldOfStudy = "Investment Banking & Corporate Finance",
                    profession = "AVP - Investment Banking",
                    company = "Global Banking Group",
                    workLocation = "Bandra-Kurla Complex (BKC), Mumbai",
                    annualIncome = "₹20–30 Lakh",
                    fatherOccupation = "Civil Engineer (Maharashtra PWD)",
                    motherOccupation = "Bank Manager (Bank of Maharashtra)",
                    brothers = 0,
                    sisters = 1,
                    familyType = "Nuclear",
                    familyValues = "Moderate",
                    familyLocation = "Dadar West, Mumbai",
                    diet = "Eggetarian",
                    smoking = "No",
                    drinking = "Socially",
                    exercise = "Pilates & Running",
                    hobbies = "Trekking Western Ghats, Contemporary Art, Specialty Coffee, Photography",
                    interests = "Art Exhibitions, Economics, Outdoor Trekking",
                    languagesKnown = "Marathi, Hindi, English",
                    aboutMe = "Balanced finance professional who loves quiet weekends as much as engaging conversations over coffee. Looking for a progressive, emotionally mature life partner with whom to build an inspiring future.",
                    avatarSeed = "PD",
                    avatarBgColorHex = 0xFF8A3043,
                    verificationStatus = "Verified",
                    interestStatus = "NONE",
                    isShortlisted = true,
                    isPublished = true,
                    lastActive = "Active 1 hour ago"
                ),
                MatrimonialProfile(
                    id = "BM104",
                    userId = "BM104",
                    fullName = "Siddharth Iyer",
                    gender = "Male",
                    age = 28,
                    dateOfBirth = "11 Feb 1997",
                    height = "5 ft 10 in (178 cm)",
                    heightInches = 70,
                    religion = "Hindu",
                    community = "Brahmin - Iyer",
                    motherTongue = "Tamil",
                    maritalStatus = "Never Married",
                    city = "Bengaluru",
                    state = "Karnataka",
                    highestQualification = "B.Tech (IIT Madras), MS (Carnegie Mellon)",
                    college = "Carnegie Mellon University",
                    fieldOfStudy = "Computer Vision & Robotics",
                    profession = "Staff AI Research Scientist",
                    company = "Autonomous Systems Lab",
                    workLocation = "Indiranagar, Bengaluru",
                    annualIncome = "₹30 Lakh+",
                    fatherOccupation = "Professor of Physics (Retd IISc)",
                    motherOccupation = "Classical Carnatic Vocalist",
                    brothers = 1,
                    sisters = 0,
                    familyType = "Nuclear",
                    familyValues = "Moderate",
                    familyLocation = "Malleshwaram, Bengaluru",
                    diet = "Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Badminton & Cycling",
                    hobbies = "Carnatic Flute, Astronomy, High-altitude Trekking, Science Fiction",
                    interests = "Space Science, Classical Instruments, Badminton",
                    languagesKnown = "Tamil, English, Hindi, Kannada",
                    aboutMe = "Curious mind grounded in South Indian cultural heritage. I appreciate thoughtful discussions, peaceful family gatherings, and mutual career encouragement. Looking for a genuine partner with shared values.",
                    avatarSeed = "SI",
                    avatarBgColorHex = 0xFF285430,
                    verificationStatus = "Verified",
                    interestStatus = "NONE",
                    isShortlisted = false,
                    isPublished = true,
                    lastActive = "Online now"
                ),
                MatrimonialProfile(
                    id = "BM105",
                    userId = "BM105",
                    fullName = "Sneha Reddy",
                    gender = "Female",
                    age = 26,
                    dateOfBirth = "19 May 1999",
                    height = "5 ft 6 in (168 cm)",
                    heightInches = 66,
                    religion = "Hindu",
                    community = "Reddy",
                    motherTongue = "Telugu",
                    maritalStatus = "Never Married",
                    city = "Hyderabad",
                    state = "Telangana",
                    highestQualification = "M.A. Public Policy & Economics",
                    college = "University of Hyderabad",
                    fieldOfStudy = "Development Economics",
                    profession = "Policy Consultant & Think-Tank Fellow",
                    company = "Center for Economic Development",
                    workLocation = "Gachibowli, Hyderabad",
                    annualIncome = "₹10–20 Lakh",
                    fatherOccupation = "Agricultural Landowner & Entrepreneur",
                    motherOccupation = "School Principal",
                    brothers = 1,
                    sisters = 1,
                    familyType = "Joint",
                    familyValues = "Traditional",
                    familyLocation = "Jubilee Hills, Hyderabad",
                    diet = "Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Kuchipudi Dance & Yoga",
                    hobbies = "Classical Kuchipudi, Reading Biographies, Gardening, Culinary Arts",
                    interests = "Public Governance, Kuchipudi, Organic Gardening",
                    languagesKnown = "Telugu, English, Hindi",
                    aboutMe = "Grounded in Telugu family traditions with a modern outlook on policy and education. I value warmth, honesty, and family togetherness above all. Looking for an educated groom from a reputable family.",
                    avatarSeed = "SR",
                    avatarBgColorHex = 0xFF9E4717,
                    verificationStatus = "Verified",
                    interestStatus = "RECEIVED",
                    isShortlisted = true,
                    isPublished = true,
                    lastActive = "Active 30 mins ago"
                ),
                MatrimonialProfile(
                    id = "BM106",
                    userId = "BM106",
                    fullName = "Tanvi Kulkarni",
                    gender = "Female",
                    age = 25,
                    dateOfBirth = "30 Jul 2000",
                    height = "5 ft 4 in (162 cm)",
                    heightInches = 64,
                    religion = "Hindu",
                    community = "Brahmin",
                    motherTongue = "Marathi",
                    maritalStatus = "Never Married",
                    city = "Pune",
                    state = "Maharashtra",
                    highestQualification = "Bachelor of Design (B.Des)",
                    college = "National Institute of Design (NID)",
                    fieldOfStudy = "Interaction & Product Design",
                    profession = "Principal UX Designer",
                    company = "Fintech Unicorn",
                    workLocation = "Kalyani Nagar, Pune",
                    annualIncome = "₹10–20 Lakh",
                    fatherOccupation = "Senior Architect",
                    motherOccupation = "Professor of English Literature",
                    brothers = 0,
                    sisters = 0,
                    familyType = "Nuclear",
                    familyValues = "Liberal",
                    familyLocation = "Prabhat Road, Pune",
                    diet = "Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Swimming & Aerial Yoga",
                    hobbies = "Watercolors, Pottery, Film Festivals, Historical Architecture",
                    interests = "Modern Architecture, Pottery, World Cinema",
                    languagesKnown = "Marathi, Hindi, English, German",
                    aboutMe = "Creative, calm, and thoughtful individual with deep appreciation for art and meaningful human connections. Seeking an open-minded companion who values kindness, humor, and intellectual partnership.",
                    avatarSeed = "TK",
                    avatarBgColorHex = 0xFF5B3076,
                    verificationStatus = "Verification Pending",
                    interestStatus = "NONE",
                    isShortlisted = false,
                    isPublished = true,
                    lastActive = "Active 3 hours ago"
                ),
                MatrimonialProfile(
                    id = "BM107",
                    userId = "BM107",
                    fullName = "Meera Rathore",
                    gender = "Female",
                    age = 26,
                    dateOfBirth = "03 Apr 1999",
                    height = "5 ft 7 in (170 cm)",
                    heightInches = 67,
                    religion = "Hindu",
                    community = "Rajput",
                    motherTongue = "Hindi",
                    maritalStatus = "Never Married",
                    city = "Jaipur",
                    state = "Rajasthan",
                    highestQualification = "B.Arch & Master of Heritage Preservation",
                    college = "CEPT University Ahmedabad",
                    fieldOfStudy = "Heritage Architecture",
                    profession = "Architectural Conservator",
                    company = "Heritage Conservation Foundation",
                    workLocation = "C-Scheme, Jaipur",
                    annualIncome = "₹10–20 Lakh",
                    fatherOccupation = "Hotelier & Real Estate",
                    motherOccupation = "Social Worker & NGO Trustee",
                    brothers = 1,
                    sisters = 0,
                    familyType = "Joint",
                    familyValues = "Traditional",
                    familyLocation = "Civil Lines, Jaipur",
                    diet = "Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Horse Riding & Aerobics",
                    hobbies = "Miniature Painting, Classical Music, Travel, Folk Embroidery",
                    interests = "Rajasthani Folk Arts, Equestrian Sports, History",
                    languagesKnown = "Hindi, English, Rajasthani",
                    aboutMe = "Proud of royal Rajasthani roots and cultural dignity. I strive to harmonize traditional respect with contemporary creative pursuits. Seeking an accomplished groom with warm family values.",
                    avatarSeed = "MR",
                    avatarBgColorHex = 0xFFB33A3A,
                    verificationStatus = "Verified",
                    interestStatus = "SENT",
                    isShortlisted = true,
                    isPublished = true,
                    lastActive = "Active 2 hours ago"
                ),
                MatrimonialProfile(
                    id = "BM108",
                    userId = "BM108",
                    fullName = "Karthik Natarajan",
                    gender = "Male",
                    age = 30,
                    dateOfBirth = "14 Nov 1995",
                    height = "5 ft 9 in (175 cm)",
                    heightInches = 69,
                    religion = "Hindu",
                    community = "Pillai",
                    motherTongue = "Tamil",
                    maritalStatus = "Never Married",
                    city = "Chennai",
                    state = "Tamil Nadu",
                    highestQualification = "B.Tech (NIT Trichy), MBA (IIM Kozhikode)",
                    college = "IIM Kozhikode",
                    fieldOfStudy = "Product Strategy & Operations",
                    profession = "Director of Product Management",
                    company = "SaaS Enterprise Solutions",
                    workLocation = "OMR IT Corridor, Chennai",
                    annualIncome = "₹30 Lakh+",
                    fatherOccupation = "Judge (Madras High Court - Retd)",
                    motherOccupation = "Government College Principal",
                    brothers = 0,
                    sisters = 1,
                    familyType = "Nuclear",
                    familyValues = "Moderate",
                    familyLocation = "Besant Nagar, Chennai",
                    diet = "Non-Vegetarian",
                    smoking = "No",
                    drinking = "No",
                    exercise = "Marathon Runner",
                    hobbies = "Distance Running, World Cinema, Chess, Podcast Host",
                    interests = "Marathon Training, World Cinema, Chess",
                    languagesKnown = "Tamil, English, Hindi",
                    aboutMe = "Energetic product leader who values personal integrity, cultural roots, and empathy. Looking for a cheerful, well-educated partner with whom to embark on life's most meaningful adventure.",
                    avatarSeed = "KN",
                    avatarBgColorHex = 0xFF2C4A52,
                    verificationStatus = "Verified",
                    interestStatus = "NONE",
                    isShortlisted = false,
                    isPublished = true,
                    lastActive = "Online now"
                )
            )
            dao.insertProfiles(sampleProfiles)

            // Seed Preferences
            dao.savePreferences(
                PartnerPreferences(
                    id = "current_user",
                    preferredAgeMin = 23,
                    preferredAgeMax = 30,
                    preferredHeightMin = 62,
                    preferredHeightMax = 72,
                    preferredLocation = "Any",
                    preferredReligion = "Any",
                    preferredCommunity = "Any",
                    preferredMotherTongue = "Any",
                    preferredEducation = "Any",
                    preferredProfession = "Any",
                    preferredIncome = "Any",
                    preferredMaritalStatus = "Never Married",
                    preferredDiet = "Any"
                )
            )

            // Seed Shortlists
            dao.insertShortlist(ShortlistRecord(profileId = "BM101", type = "MY_SHORTLIST"))
            dao.insertShortlist(ShortlistRecord(profileId = "BM103", type = "MY_SHORTLIST"))
            dao.insertShortlist(ShortlistRecord(profileId = "BM105", type = "SHORTLISTED_ME"))
            dao.insertShortlist(ShortlistRecord(profileId = "BM107", type = "SHORTLISTED_ME"))

            // Seed Interests
            dao.insertInterest(InterestRecord(targetProfileId = "BM102", direction = "RECEIVED", status = "ACCEPTED"))
            dao.insertInterest(InterestRecord(targetProfileId = "BM105", direction = "RECEIVED", status = "SENT"))
            dao.insertInterest(InterestRecord(targetProfileId = "BM107", direction = "SENT", status = "SENT"))

            // Seed Conversations
            dao.insertConversation(
                Conversation(
                    id = "BM102",
                    participantName = "Dr. Rohan Malhotra",
                    participantProfession = "Cardiologist",
                    participantCity = "Delhi",
                    participantAvatarSeed = "RM",
                    participantBgColorHex = 0xFF194D66,
                    lastMessage = "Namaste! Our families would love to connect over a call this weekend.",
                    lastMessageTimestamp = System.currentTimeMillis() - 1000 * 60 * 25,
                    unreadCount = 1,
                    isOnline = true
                )
            )

            // Seed Messages
            dao.insertMessage(
                ChatMessage(
                    conversationId = "BM102",
                    senderId = "BM102",
                    receiverId = "current_user",
                    messageText = "Hello! Thank you for accepting my interest on Bondhon.",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 60
                )
            )
            dao.insertMessage(
                ChatMessage(
                    conversationId = "BM102",
                    senderId = "current_user",
                    receiverId = "BM102",
                    messageText = "Namaste Dr. Rohan. Glad to connect. Your family background resonates well with ours.",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 45
                )
            )
            dao.insertMessage(
                ChatMessage(
                    conversationId = "BM102",
                    senderId = "BM102",
                    receiverId = "current_user",
                    messageText = "Namaste! Our families would love to connect over a call this weekend.",
                    timestamp = System.currentTimeMillis() - 1000 * 60 * 25
                )
            )

            // Seed Profile Visitors
            val now = System.currentTimeMillis()
            dao.insertVisitor(ProfileVisitor(profileId = "BM101", name = "Ananya Chatterjee", age = 26, location = "Kolkata", profession = "Software Engineer", avatarSeed = "AC", avatarBgColorHex = 0xFF7A1228, timestamp = now - 1000 * 60 * 40))
            dao.insertVisitor(ProfileVisitor(profileId = "BM103", name = "Priyanka Deshmukh", age = 27, location = "Mumbai", profession = "Investment Banking", avatarSeed = "PD", avatarBgColorHex = 0xFF8A3043, timestamp = now - 1000 * 60 * 120))
            dao.insertVisitor(ProfileVisitor(profileId = "BM105", name = "Sneha Reddy", age = 26, location = "Hyderabad", profession = "Policy Consultant", avatarSeed = "SR", avatarBgColorHex = 0xFF9E4717, timestamp = now - 1000 * 60 * 300))

            // Seed Notifications
            dao.insertNotification(NotificationItem(title = "Interest Accepted!", message = "Dr. Rohan Malhotra accepted your interest. You can now chat directly.", type = "ACCEPTED", timestamp = now - 1000 * 60 * 70, isRead = false, targetProfileId = "BM102"))
            dao.insertNotification(NotificationItem(title = "New Matrimonial Interest", message = "Sneha Reddy from Hyderabad has expressed interest in your profile.", type = "INTEREST", timestamp = now - 1000 * 60 * 180, isRead = false, targetProfileId = "BM105"))
            dao.insertNotification(NotificationItem(title = "Profile Shortlisted", message = "Ananya Chatterjee added you to her shortlist.", type = "SHORTLIST", timestamp = now - 1000 * 60 * 360, isRead = true, targetProfileId = "BM101"))

            // Seed Reports
            val sampleReports = listOf(
                UserReport(
                    reportedUserId = "BM199",
                    reportedUserName = "Vikram Saxena (Suspicious Profile)",
                    reportedByUserId = "current_user",
                    reportedByName = "Dr. Malhotra",
                    reason = "Fake Profile / Incomplete Details",
                    details = "User is not responding to calls and photo appears to be a stock model image.",
                    date = "28 Sep 2026",
                    status = "Pending Review"
                )
            )
            for (report in sampleReports) {
                dao.insertReport(report)
            }
        }
    }
}
