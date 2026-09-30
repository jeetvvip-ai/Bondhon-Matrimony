package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MatrimonialProfile
import com.example.ui.MatrimonyViewModel
import com.example.ui.components.ProfileAvatar
import com.example.ui.components.ReportProfileDialog
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldAccentDark
import com.example.ui.theme.GoldAccentLight
import com.example.ui.theme.RoyalMaroonDark
import com.example.ui.theme.RoyalMaroonPrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.VerifiedBlue
import com.example.ui.theme.WarmCreamBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailedProfileScreen(
    profile: MatrimonialProfile,
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val matchResult = viewModel.calculateMatch(profile)

    var showReportDialog by remember { mutableStateOf(false) }
    var showReportPhotoDialog by remember { mutableStateOf(false) }
    var showBlockConfirmDialog by remember { mutableStateOf(false) }
    var selectedPhotoIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp)
    ) {
        // Hero Photo & Name Header Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Bar with Back & Shortlist
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("profile_detail_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = RoyalMaroonPrimary
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFDE8EC),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccentLight)
                    ) {
                        Text(
                            text = "Matrimony ID: ${profile.id}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = RoyalMaroonPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    IconButton(
                        onClick = { viewModel.toggleShortlist(profile) },
                        modifier = Modifier.testTag("profile_detail_shortlist_btn")
                    ) {
                        Icon(
                            imageVector = if (profile.isShortlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Shortlist",
                            tint = RoyalMaroonPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Avatar Photo
                ProfileAvatar(
                    name = profile.fullName,
                    seed = profile.avatarSeed,
                    bgColorHex = profile.avatarBgColorHex,
                    size = 100.dp,
                    isVerified = profile.verificationStatus == "Verified"
                )

                // Additional Photo Gallery Bar
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (0 until profile.additionalPhotosCount.coerceAtLeast(1)).forEach { idx ->
                        Surface(
                            shape = CircleShape,
                            color = if (selectedPhotoIndex == idx) RoyalMaroonPrimary else Color(0xFFEADBCE),
                            modifier = Modifier
                                .size(8.dp)
                                .clickable { selectedPhotoIndex = idx }
                        ) {}
                    }

                    Spacer(modifier = Modifier.width(6.dp))
                    TextButton(onClick = { showReportPhotoDialog = true }) {
                        Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(13.dp), tint = TextSecondaryMuted)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Report Photo", fontSize = 10.sp, color = TextSecondaryMuted)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = profile.fullName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryDark
                    )
                    if (profile.verificationStatus == "Verified") {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified Profile",
                            tint = VerifiedBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Text(
                    text = "${profile.age} yrs • ${profile.height} • ${profile.maritalStatus}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = RoyalMaroonPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${profile.city}, ${profile.state}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimaryDark
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Match Percentage & Explanation Pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFFF7EA),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = GoldAccentDark, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${matchResult.percentage}% Match Compatibility",
                                fontWeight = FontWeight.Bold,
                                color = RoyalMaroonDark,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            matchResult.matchingTags.forEach { tag ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.White
                                ) {
                                    Text(
                                        text = "✓ $tag",
                                        fontSize = 11.sp,
                                        color = RoyalMaroonPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = matchResult.disclaimer,
                            fontSize = 10.sp,
                            color = TextSecondaryMuted,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Primary Action Buttons Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (profile.interestStatus == "ACCEPTED") {
                        Button(
                            onClick = { viewModel.openChatWithProfile(profile) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("detail_chat_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Message", fontWeight = FontWeight.Bold)
                        }
                    } else if (profile.interestStatus == "SENT") {
                        Button(
                            onClick = {},
                            enabled = false,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = Color(0xFFEDE5DE),
                                disabledContentColor = TextSecondaryMuted
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Interest Sent")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.sendInterest(profile) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("detail_send_interest_btn"),
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = GoldAccentLight, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Send Interest", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            if (profile.interestStatus == "ACCEPTED") {
                                viewModel.openChatWithProfile(profile)
                            } else {
                                viewModel.sendInterest(profile)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("detail_secondary_msg_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = RoyalMaroonPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (profile.interestStatus == "ACCEPTED") "Chat" else "Express Interest",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Detail Sections
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. About Me
            DetailSectionCard(title = "About Me", icon = Icons.Default.Person) {
                Text(
                    text = profile.aboutMe,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimaryDark,
                    lineHeight = 22.sp
                )
            }

            // 2. Basic Information
            DetailSectionCard(title = "Basic Information", icon = Icons.Default.Info) {
                DetailRow("Full Name", profile.fullName)
                DetailRow("Gender", profile.gender)
                DetailRow("Date of Birth", profile.dateOfBirth)
                DetailRow("Age", "${profile.age} Years")
                DetailRow("Height", profile.height)
                DetailRow("Religion", profile.religion)
                DetailRow("Caste / Community", profile.community)
                DetailRow("Mother Tongue", profile.motherTongue)
                DetailRow("Marital Status", profile.maritalStatus)
                DetailRow("City & State", "${profile.city}, ${profile.state}")
                DetailRow("Country", profile.country)
            }

            // 3. Education & Career
            DetailSectionCard(title = "Education & Career", icon = Icons.Default.School) {
                DetailRow("Highest Qualification", profile.highestQualification)
                DetailRow("College / University", profile.college)
                if (profile.fieldOfStudy.isNotBlank()) {
                    DetailRow("Field of Study", profile.fieldOfStudy)
                }
                DetailRow("Profession", profile.profession)
                DetailRow("Company Name", profile.company)
                DetailRow("Work Location", profile.workLocation)
                DetailRow("Annual Income", profile.annualIncome)
            }

            // 4. Family Details
            DetailSectionCard(title = "Family Background", icon = Icons.Default.FamilyRestroom) {
                DetailRow("Father's Occupation", profile.fatherOccupation)
                DetailRow("Mother's Occupation", profile.motherOccupation)
                DetailRow("Brothers", "${profile.brothers} brother(s)")
                DetailRow("Sisters", "${profile.sisters} sister(s)")
                DetailRow("Family Type", profile.familyType)
                DetailRow("Family Values", profile.familyValues)
                DetailRow("Family Location", profile.familyLocation)
            }

            // 5. Lifestyle & Habits
            DetailSectionCard(title = "Lifestyle & Habits", icon = Icons.Default.Restaurant) {
                DetailRow("Diet / Food Preference", profile.diet)
                DetailRow("Smoking", profile.smoking)
                DetailRow("Drinking", profile.drinking)
                DetailRow("Physical Fitness", profile.exercise)
                DetailRow("Hobbies", profile.hobbies)
                DetailRow("Interests", profile.interests)
                DetailRow("Languages Known", profile.languagesKnown)
            }

            // 6. Partner Preferences
            DetailSectionCard(title = "Partner Preferences", icon = Icons.Default.Favorite) {
                DetailRow("Preferred Age", "22 to 32 Years")
                DetailRow("Preferred Height", "5 ft 2 in to 6 ft 2 in")
                DetailRow("Preferred Education", "Graduate / Post Graduate")
                DetailRow("Preferred Profession", "Any Professional / Corporate / Govt")
                DetailRow("Preferred Marital Status", "Never Married")
                DetailRow("Preferred Diet", "Vegetarian / Flexible")
            }

            // 7. Trust, Contact & Safety
            DetailSectionCard(title = "Contact & Privacy Settings", icon = Icons.Default.Lock) {
                DetailRow("Photo Privacy", profile.photoPrivacy)
                DetailRow("Phone / Contact", "Protected • Available upon mutual accepted interest")
                DetailRow("Verification Status", "${profile.verificationStatus} ✓")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Report & Block Profile Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = { showReportDialog = true },
                    modifier = Modifier.testTag("report_profile_btn")
                ) {
                    Icon(Icons.Default.Report, contentDescription = null, tint = Color(0xFFC0392B), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Report Profile", color = Color(0xFFC0392B), fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(
                    onClick = { showBlockConfirmDialog = true },
                    modifier = Modifier.testTag("block_profile_btn")
                ) {
                    Icon(Icons.Default.Block, contentDescription = null, tint = TextSecondaryMuted, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Block Profile", color = TextSecondaryMuted, fontSize = 12.sp)
                }
            }
        }
    }

    // Report Profile Dialog
    if (showReportDialog) {
        ReportProfileDialog(
            profileName = profile.fullName,
            onDismiss = { showReportDialog = false },
            onSubmitReport = { reason, details ->
                viewModel.reportProfile(profile.id, profile.fullName, reason, details)
                showReportDialog = false
            }
        )
    }

    // Report Photo Dialog
    if (showReportPhotoDialog) {
        AlertDialog(
            onDismissRequest = { showReportPhotoDialog = false },
            title = { Text("Report Inappropriate Photo?") },
            text = { Text("Flag this photograph for review by Bondhon Matrimony Trust Team. Inappropriate or sensitive photos violate community policy.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.reportProfile(profile.id, profile.fullName, "Inappropriate Photo", "Flagged photo index $selectedPhotoIndex")
                        showReportPhotoDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary)
                ) {
                    Text("Report Photo")
                }
            },
            dismissButton = {
                TextButton(onClick = { showReportPhotoDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Block Confirmation Dialog
    if (showBlockConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showBlockConfirmDialog = false },
            title = { Text("Block ${profile.fullName}?") },
            text = { Text("They will no longer be able to view your profile, send interests or message you on Bondhon Matrimony.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.blockProfile(profile.id, profile.fullName)
                        showBlockConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary)
                ) {
                    Text("Block")
                }
            },
            dismissButton = {
                TextButton(onClick = { showBlockConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun DetailSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = RoyalMaroonPrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )
            }
            content()
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryMuted,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = TextPrimaryDark,
            modifier = Modifier.weight(1.2f)
        )
    }
}
