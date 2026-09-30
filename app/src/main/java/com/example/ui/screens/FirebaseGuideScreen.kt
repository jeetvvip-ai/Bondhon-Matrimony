package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MatrimonyViewModel
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldAccentDark
import com.example.ui.theme.RoyalMaroonPrimary
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarmCreamBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseGuideScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Firebase Architecture & Setup",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = RoyalMaroonPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Cloud, contentDescription = null, tint = RoyalMaroonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cloud-Ready Architecture",
                            fontWeight = FontWeight.Bold,
                            color = RoyalMaroonPrimary,
                            fontSize = 15.sp
                        )
                    }
                    Text(
                        text = "Bondhon Matrimony is engineered with a clean Repository pattern that decouples the UI layer from persistence. The local Room entities map 1:1 to cloud Firebase Firestore collections.",
                        fontSize = 12.sp,
                        color = TextPrimaryDark,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            GuideSection(
                icon = Icons.Default.Storage,
                title = "1. Firestore Collections Schema",
                content = """
                    The database is organized into the following collections:
                    • users: User accounts, roles, auth provider, created timestamp
                    • profiles: Complete matrimonial profile details (education, family, lifestyle, photos)
                    • partnerPreferences: Preferred age, height, education, religion, locations
                    • interests: Directed matrimonial interests (status: SENT, ACCEPTED, DECLINED)
                    • shortlists: Saved profiles (MY_SHORTLIST, SHORTLISTED_ME)
                    • conversations: Active chat metadata, participant IDs, last message
                    • messages: Sub-collection or top-level message logs between connected users
                    • notifications: Push and in-app notifications
                    • verificationRequests: Photo ID proofs, marital status documents, approval queue
                    • reports: Profile flags, moderation queue, trust reviews
                    • adminUsers: Authorized admin user credentials
                """.trimIndent()
            )

            GuideSection(
                icon = Icons.Default.Lock,
                title = "2. Security Rules (Zero-Trust Model)",
                content = """
                    • Profiles: Publicly readable by authenticated members; writable only by profile owner (request.auth.uid == resource.data.userId).
                    • Interests: Only participants (fromUserId or toUserId) can read or update interest status.
                    • Messages: Strictly accessible only if a mutual ACCEPTED interest exists between both users.
                    • Reports & Moderation: Only users with admin claim (request.auth.token.admin == true) can mutate.
                """.trimIndent()
            )

            GuideSection(
                icon = Icons.Default.Notifications,
                title = "3. Cloud Messaging (FCM)",
                content = """
                    Set up Firebase Cloud Messaging (FCM) to deliver instant push notifications for:
                    • New Matrimonial Interest Received
                    • Interest Accepted Celebration
                    • Direct message from mutual connection
                    • Profile verification status updates
                """.trimIndent()
            )

            GuideSection(
                icon = Icons.Default.Code,
                title = "4. Connecting Firebase Steps",
                content = """
                    1. Create a Firebase project at console.firebase.google.com.
                    2. Add an Android app with package: com.aistudio.bondhonmatrimony.bmkvd.
                    3. Download google-services.json into /app/ directory.
                    4. In app/build.gradle.kts, uncomment implementation(libs.firebase.firestore) and implementation(libs.firebase.auth).
                    5. Deploy firestore.rules using the Firebase CLI: firebase deploy --only firestore:rules.
                """.trimIndent()
            )
        }
    }
}

@Composable
fun GuideSection(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = RoyalMaroonPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary, fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(content, fontSize = 12.sp, color = TextPrimaryDark, lineHeight = 18.sp)
        }
    }
}
