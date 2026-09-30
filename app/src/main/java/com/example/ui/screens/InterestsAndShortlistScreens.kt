package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MatrimonialProfile
import com.example.ui.MatrimonyViewModel
import com.example.ui.Screen
import com.example.ui.components.ProfileAvatar
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldAccentDark
import com.example.ui.theme.GoldAccentLight
import com.example.ui.theme.RoyalMaroonDark
import com.example.ui.theme.RoyalMaroonPrimary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarmCreamBackground

@Composable
fun InterestsScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val allProfiles by viewModel.allProfiles.collectAsState()

    // Received Interests (Profiles with interest status RECEIVED or ACCEPTED)
    val receivedProfiles = allProfiles.filter { it.interestStatus == "RECEIVED" || it.interestStatus == "ACCEPTED" }
    // Sent Interests (Profiles with interest status SENT)
    val sentProfiles = allProfiles.filter { it.interestStatus == "SENT" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = RoyalMaroonPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = RoyalMaroonPrimary
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Received (${receivedProfiles.size})", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("interests_received_tab")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Sent (${sentProfiles.size})", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("interests_sent_tab")
            )
        }

        if (selectedTab == 0) {
            // Received Tab
            if (receivedProfiles.isEmpty()) {
                EmptyInterestsState(
                    title = "No received interests yet",
                    message = "When other members express interest in your profile, they will appear here."
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(receivedProfiles) { profile ->
                        ReceivedInterestCard(
                            profile = profile,
                            onAccept = { viewModel.acceptInterest(profile) },
                            onDecline = { viewModel.declineInterest(profile.id) },
                            onMaybeLater = { viewModel.maybeLaterInterest(profile.id) },
                            onViewProfile = { viewModel.openProfileDetail(profile) },
                            onChat = { viewModel.openChatWithProfile(profile) }
                        )
                    }
                }
            }
        } else {
            // Sent Tab
            if (sentProfiles.isEmpty()) {
                EmptyInterestsState(
                    title = "No pending sent interests",
                    message = "Browse recommended matches and send interests to prospective partners."
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sentProfiles) { profile ->
                        SentInterestCard(
                            profile = profile,
                            onViewProfile = { viewModel.openProfileDetail(profile) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReceivedInterestCard(
    profile: MatrimonialProfile,
    onAccept: () -> Unit,
    onDecline: () -> Unit,
    onMaybeLater: () -> Unit,
    onViewProfile: () -> Unit,
    onChat: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onViewProfile() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProfileAvatar(
                    name = profile.fullName,
                    seed = profile.avatarSeed,
                    bgColorHex = profile.avatarBgColorHex,
                    size = 56.dp,
                    isVerified = profile.verificationStatus == "Verified"
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = profile.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryDark
                    )
                    Text(
                        text = "${profile.age} yrs • ${profile.height} • ${profile.city}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryMuted
                    )
                    Text(
                        text = "${profile.profession} (${profile.highestQualification})",
                        style = MaterialTheme.typography.bodySmall,
                        color = RoyalMaroonPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (profile.interestStatus == "ACCEPTED") {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFE8F5E9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "You are now connected! Mutual interest accepted.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onChat,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Chat Directly with ${profile.fullName}")
                }
            } else {
                Text(
                    text = "${profile.fullName} has expressed interest in your matrimonial profile.",
                    fontSize = 12.sp,
                    color = TextSecondaryMuted
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1.2f),
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Accept", fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDecline,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Decline", color = TextSecondaryMuted)
                    }

                    TextButton(
                        onClick = onMaybeLater,
                        modifier = Modifier.weight(1.1f)
                    ) {
                        Text("Maybe Later", fontSize = 11.sp, color = RoyalMaroonPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun SentInterestCard(
    profile: MatrimonialProfile,
    onViewProfile: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onViewProfile() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAvatar(
                name = profile.fullName,
                seed = profile.avatarSeed,
                bgColorHex = profile.avatarBgColorHex,
                size = 50.dp,
                isVerified = profile.verificationStatus == "Verified"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(profile.fullName, fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                Text("${profile.age} yrs • ${profile.city} • ${profile.profession}", fontSize = 12.sp, color = TextSecondaryMuted)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 2.dp)) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = GoldAccentDark, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Interest Sent • Awaiting Response", fontSize = 11.sp, color = GoldAccentDark, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun ShortlistScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val allProfiles by viewModel.allProfiles.collectAsState()

    val myShortlist = allProfiles.filter { it.isShortlisted }
    val shortlistedMe = allProfiles.filter { it.id == "BM105" || it.id == "BM107" || it.id == "BM101" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            contentColor = RoyalMaroonPrimary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("My Shortlist (${myShortlist.size})", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("my_shortlist_tab")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Who Shortlisted Me (${shortlistedMe.size})", fontWeight = FontWeight.Bold) },
                modifier = Modifier.testTag("shortlisted_me_tab")
            )
        }

        val listToDisplay = if (selectedTab == 0) myShortlist else shortlistedMe

        if (listToDisplay.isEmpty()) {
            EmptyInterestsState(
                title = "Shortlist is empty",
                message = "Tap the heart icon on any profile to shortlist matches you want to review later."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listToDisplay) { profile ->
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { viewModel.openProfileDetail(profile) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileAvatar(
                                name = profile.fullName,
                                seed = profile.avatarSeed,
                                bgColorHex = profile.avatarBgColorHex,
                                size = 52.dp,
                                isVerified = profile.verificationStatus == "Verified"
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(profile.fullName, fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                                Text("${profile.age} yrs • ${profile.height}", fontSize = 12.sp, color = TextSecondaryMuted)
                                Text("${profile.city} • ${profile.profession}", fontSize = 12.sp, color = RoyalMaroonPrimary)
                            }

                            Row {
                                if (selectedTab == 0) {
                                    IconButton(onClick = { viewModel.toggleShortlist(profile) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remove", tint = TextSecondaryMuted)
                                    }
                                }
                                Button(
                                    onClick = { viewModel.openProfileDetail(profile) },
                                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("View", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileVisitorsScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val visitors by viewModel.visitors.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        // Premium Visitor Banner
        Surface(
            color = Color(0xFFFFF9EE),
            modifier = Modifier.fillMaxWidth(),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccentLight)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Visibility, contentDescription = null, tint = RoyalMaroonPrimary, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Profile Visitors Insight", fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary, fontSize = 14.sp)
                    Text("Showing prospective partners who viewed your profile recently.", fontSize = 11.sp, color = TextSecondaryMuted)
                }
            }
        }

        if (visitors.isEmpty()) {
            EmptyInterestsState(
                title = "No visitors recorded yet",
                message = "As more prospective matches view your profile, they will be listed here."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(visitors) { visitor ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ProfileAvatar(name = visitor.name, seed = visitor.avatarSeed, bgColorHex = visitor.avatarBgColorHex, size = 46.dp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(visitor.name, fontWeight = FontWeight.Bold, color = TextPrimaryDark)
                                Text("${visitor.age} yrs • ${visitor.location}", fontSize = 12.sp, color = TextSecondaryMuted)
                                Text(visitor.profession, fontSize = 11.sp, color = RoyalMaroonPrimary)
                            }
                            Text("Recently", fontSize = 10.sp, color = TextSecondaryMuted)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyInterestsState(title: String, message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFDFD1C4), modifier = Modifier.size(54.dp))
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimaryDark)
            Text(message, fontSize = 12.sp, color = TextSecondaryMuted, textAlign = androidx.compose.ui.text.style.TextAlign.Center, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
