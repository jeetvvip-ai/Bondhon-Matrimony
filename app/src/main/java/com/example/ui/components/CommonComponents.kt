package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.MatchCalculationResult
import com.example.data.model.MatrimonialProfile
import com.example.ui.Screen
import com.example.ui.theme.CardBorderGold
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

@Composable
fun BondhonLogo(
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    showText: Boolean = true,
    tagline: String? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Abstract interlocking bond knot emblem
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(size / 3))
                .background(
                    Brush.linearGradient(
                        listOf(RoyalMaroonPrimary, RoyalMaroonDark)
                    )
                )
                .border(1.5.dp, GoldAccent, RoundedCornerShape(size / 3)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(size * 0.45f)
                        .clip(CircleShape)
                        .border(1.8.dp, GoldAccentLight, CircleShape)
                )
                Spacer(modifier = Modifier.width((-size * 0.15f)))
                Box(
                    modifier = Modifier
                        .size(size * 0.45f)
                        .clip(CircleShape)
                        .border(1.8.dp, Color(0xFFFFF2D0), CircleShape)
                )
            }
        }

        if (showText) {
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Bondhon",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = (size.value * 0.48).sp,
                        color = RoyalMaroonPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Matrimony",
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium,
                        fontSize = (size.value * 0.38).sp,
                        color = GoldAccentDark
                    )
                }
                if (tagline != null) {
                    Text(
                        text = tagline,
                        fontSize = 11.sp,
                        color = TextSecondaryMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileAvatar(
    name: String,
    seed: String,
    bgColorHex: Long,
    size: Dp = 64.dp,
    isVerified: Boolean = false,
    modifier: Modifier = Modifier
) {
    val initials = if (seed.isNotBlank()) seed else {
        name.split(" ").take(2).mapNotNull { it.firstOrNull()?.uppercaseChar() }.joinToString("")
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(bgColorHex),
                            Color(bgColorHex).copy(alpha = 0.85f)
                        )
                    )
                )
                .border(2.dp, GoldAccentLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = Color(0xFFFFF7EA),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = (size.value * 0.38).sp,
                letterSpacing = 1.sp
            )
        }

        if (isVerified) {
            Box(
                modifier = Modifier
                    .size((size.value * 0.35).dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                    .padding(1.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified Profile",
                    tint = VerifiedBlue,
                    modifier = Modifier.size((size.value * 0.30).dp)
                )
            }
        }
    }
}

@Composable
fun MatchScoreBadge(score: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFFFF2D6),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = GoldAccentDark,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "$score% Match",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = RoyalMaroonDark
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileCard(
    profile: MatrimonialProfile,
    matchResult: MatchCalculationResult,
    onViewProfile: () -> Unit,
    onSendInterest: () -> Unit,
    onToggleShortlist: () -> Unit,
    onStartChat: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onViewProfile() }
            .testTag("profile_card_${profile.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Avatar, Name, Age, Location & Match Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                ProfileAvatar(
                    name = profile.fullName,
                    seed = profile.avatarSeed,
                    bgColorHex = profile.avatarBgColorHex,
                    size = 64.dp,
                    isVerified = profile.verificationStatus == "Verified"
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = profile.fullName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        IconButton(
                            onClick = onToggleShortlist,
                            modifier = Modifier
                                .size(32.dp)
                                .testTag("shortlist_btn_${profile.id}")
                        ) {
                            Icon(
                                imageVector = if (profile.isShortlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Shortlist",
                                tint = if (profile.isShortlisted) RoyalMaroonPrimary else TextSecondaryMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Text(
                        text = "${profile.age} yrs • ${profile.height}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryMuted
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = RoyalMaroonPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${profile.city}, ${profile.state}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondaryMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Professional & Cultural Info Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Education / Profession
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = null,
                        tint = GoldAccentDark,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${profile.profession} (${profile.annualIncome})",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextPrimaryDark,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Religion & Caste
                Text(
                    text = "${profile.religion}, ${profile.community}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Match Score & Key Tags
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MatchScoreBadge(score = matchResult.percentage)
                Spacer(modifier = Modifier.width(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    matchResult.matchingTags.take(2).forEach { tag ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF3EDE6)
                        ) {
                            Text(
                                text = "✓ $tag",
                                fontSize = 10.sp,
                                color = TextSecondaryMuted,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onViewProfile,
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .testTag("view_profile_btn_${profile.id}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = RoyalMaroonPrimary
                    )
                ) {
                    Text(text = "View Profile", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                if (profile.interestStatus == "ACCEPTED") {
                    Button(
                        onClick = { onStartChat?.invoke() },
                        modifier = Modifier
                            .weight(1.2f)
                            .height(38.dp)
                            .testTag("chat_btn_${profile.id}"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SuccessGreen
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Connected • Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                } else if (profile.interestStatus == "SENT") {
                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier
                            .weight(1.2f)
                            .height(38.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = Color(0xFFEDE5DE),
                            disabledContentColor = TextSecondaryMuted
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Interest Sent", fontSize = 12.sp)
                    }
                } else {
                    Button(
                        onClick = onSendInterest,
                        modifier = Modifier
                            .weight(1.2f)
                            .height(38.dp)
                            .testTag("send_interest_btn_${profile.id}"),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RoyalMaroonPrimary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = GoldAccentLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Send Interest",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BondhonAppBar(
    title: String,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    unreadNotificationCount: Int,
    onNotificationsClick: () -> Unit,
    isAdminMode: Boolean,
    onAdminClick: () -> Unit,
    onOpenFirebaseGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (canNavigateBack) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )
            } else {
                BondhonLogo(size = 32.dp, tagline = "Trust • Family • Bond")
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("top_bar_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = RoyalMaroonPrimary
                    )
                }
            }
        },
        actions = {
            // Firebase setup guide button
            IconButton(
                onClick = onOpenFirebaseGuide,
                modifier = Modifier.testTag("top_bar_firebase_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "Firebase Architecture & Guide",
                    tint = GoldAccentDark,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Admin Portal button
            IconButton(
                onClick = onAdminClick,
                modifier = Modifier.testTag("top_bar_admin_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = "Admin Panel",
                    tint = if (isAdminMode) RoyalMaroonPrimary else TextSecondaryMuted,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Notification Bell with Badge
            IconButton(
                onClick = onNotificationsClick,
                modifier = Modifier.testTag("top_bar_notifications_btn")
            ) {
                BadgedBox(
                    badge = {
                        if (unreadNotificationCount > 0) {
                            Badge(
                                containerColor = RoyalMaroonPrimary,
                                contentColor = Color.White
                            ) {
                                Text(text = "$unreadNotificationCount")
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = RoyalMaroonPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = RoyalMaroonPrimary
        )
    )
}

@Composable
fun BondhonBottomBar(
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit,
    unreadMessageCount: Int = 0,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
        tonalElevation = 6.dp
    ) {
        val items = listOf(
            Triple(Screen.DASHBOARD, Icons.Filled.Home to Icons.Outlined.Home, "Home"),
            Triple(Screen.FIND_MATCHES, Icons.Filled.Search to Icons.Outlined.Search, "Search"),
            Triple(Screen.INTERESTS, Icons.Filled.Favorite to Icons.Outlined.FavoriteBorder, "Interests"),
            Triple(Screen.MESSAGES, Icons.AutoMirrored.Filled.Chat to Icons.Outlined.Chat, "Messages"),
            Triple(Screen.SAFETY_PRIVACY, Icons.Filled.Person to Icons.Outlined.Person, "My Account")
        )

        items.forEach { (screen, icons, label) ->
            val isSelected = currentScreen == screen ||
                    (screen == Screen.DASHBOARD && currentScreen == Screen.LANDING) ||
                    (screen == Screen.SAFETY_PRIVACY && (currentScreen == Screen.SETTINGS || currentScreen == Screen.VERIFICATION || currentScreen == Screen.PROFILE_WIZARD))

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(screen) },
                icon = {
                    if (screen == Screen.MESSAGES && unreadMessageCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = RoyalMaroonPrimary,
                                    contentColor = Color.White
                                ) {
                                    Text(text = "$unreadMessageCount")
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSelected) icons.first else icons.second,
                                contentDescription = label
                            )
                        }
                    } else {
                        Icon(
                            imageVector = if (isSelected) icons.first else icons.second,
                            contentDescription = label
                        )
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = RoyalMaroonPrimary,
                    selectedTextColor = RoyalMaroonPrimary,
                    indicatorColor = Color(0xFFFEE8EC),
                    unselectedIconColor = TextSecondaryMuted,
                    unselectedTextColor = TextSecondaryMuted
                ),
                modifier = Modifier.testTag("nav_item_${label.lowercase().replace(" ", "_")}")
            )
        }
    }
}

@Composable
fun ReportProfileDialog(
    profileName: String,
    onDismiss: () -> Unit,
    onSubmitReport: (reason: String, details: String) -> Unit
) {
    val reasons = listOf(
        "Fake Profile / Misleading Photos",
        "Harassment / Disrespectful Conduct",
        "Scam / Financial or Money Request",
        "Commercial / Casual Dating Solicitation",
        "Inappropriate Content / Privacy Violation",
        "Other Inappropriate Behavior"
    )

    var selectedReason by remember { mutableStateOf(reasons.first()) }
    var details by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Report Profile",
                    style = MaterialTheme.typography.titleLarge,
                    color = RoyalMaroonPrimary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Report $profileName to Bondhon Trust & Safety Team. All reports are strictly confidential.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryMuted,
                    modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                )

                Text(
                    text = "Select Reason:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                reasons.forEach { r ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedReason = r }
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    if (selectedReason == r) RoyalMaroonPrimary else TextSecondaryMuted,
                                    CircleShape
                                )
                                .padding(2.dp)
                                .background(if (selectedReason == r) RoyalMaroonPrimary else Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = r,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimaryDark
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("Additional details (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onSubmitReport(selectedReason, details) },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary)
                    ) {
                        Text("Submit Report", color = Color.White)
                    }
                }
            }
        }
    }
}
