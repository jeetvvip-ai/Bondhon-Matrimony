package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.Visibility
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.example.ui.FilterCriteria
import com.example.ui.MatrimonyViewModel
import com.example.ui.Screen
import com.example.ui.components.ProfileAvatar
import com.example.ui.components.ProfileCard
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
fun DashboardScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val myProfile by viewModel.myProfile.collectAsState()
    val publishedProfiles by viewModel.publishedProfiles.collectAsState()
    val visitors by viewModel.visitors.collectAsState()
    val interests by viewModel.interests.collectAsState()
    val shortlists by viewModel.shortlists.collectAsState()

    val otherProfiles = publishedProfiles.filter { it.id != myProfile.id }
    val recommendedProfiles = otherProfiles.take(4)
    val newProfiles = otherProfiles.takeLast(3)

    val receivedCount = interests.count { it.direction == "RECEIVED" }
    val sentCount = interests.count { it.direction == "SENT" }
    val shortlistedCount = shortlists.count { it.type == "MY_SHORTLIST" }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Welcome Header & Profile Completion
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_greeting_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileAvatar(
                            name = myProfile.fullName,
                            seed = myProfile.avatarSeed,
                            bgColorHex = myProfile.avatarBgColorHex,
                            size = 54.dp,
                            isVerified = myProfile.verificationStatus == "Verified"
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Welcome, ${myProfile.fullName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = RoyalMaroonPrimary
                            )
                            Text(
                                text = "${myProfile.city.ifBlank { "Location unset" }} • Member ID: ${myProfile.id}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryMuted
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFE8F5E9),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen)
                        ) {
                            Text(
                                text = "100% Free Member",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Profile Completion Percentage Bar
                    val completionPercent = if (myProfile.isPublished) 85 else 40
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFFAF4ED))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Profile Completion: ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextPrimaryDark
                                )
                                Text(
                                    text = "$completionPercent% Complete",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    color = RoyalMaroonPrimary
                                )
                            }
                            TextButton(
                                onClick = { viewModel.navigateTo(Screen.PROFILE_WIZARD) },
                                modifier = Modifier.testTag("complete_profile_btn")
                            ) {
                                Text("Edit Profile", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            }
                        }

                        LinearProgressIndicator(
                            progress = { completionPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = RoyalMaroonPrimary,
                            trackColor = Color(0xFFEADFD4)
                        )

                        Text(
                            text = if (myProfile.isPublished) {
                                "Your profile is published and live in search results. All features are 100% free."
                            } else {
                                "Complete your required profile details to appear in public search results."
                            },
                            fontSize = 11.sp,
                            color = TextSecondaryMuted,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }
        }

        // 2. Activity Metrics (Interests, Shortlists, Visitors)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricCard(
                    title = "Interests Rcvd",
                    count = "$receivedCount",
                    subtitle = "Responses",
                    icon = Icons.Default.Favorite,
                    iconTint = RoyalMaroonPrimary,
                    onClick = { viewModel.navigateTo(Screen.INTERESTS) },
                    modifier = Modifier.weight(1f)
                )

                MetricCard(
                    title = "Interests Sent",
                    count = "$sentCount",
                    subtitle = "Awaiting",
                    icon = Icons.Default.Mail,
                    iconTint = GoldAccentDark,
                    onClick = { viewModel.navigateTo(Screen.INTERESTS) },
                    modifier = Modifier.weight(1f)
                )

                MetricCard(
                    title = "Shortlisted",
                    count = "$shortlistedCount",
                    subtitle = "Saved",
                    icon = Icons.Default.Star,
                    iconTint = Color(0xFFE67E22),
                    onClick = { viewModel.navigateTo(Screen.SHORTLIST) },
                    modifier = Modifier.weight(1f)
                )

                MetricCard(
                    title = "Visitors",
                    count = "${visitors.size}",
                    subtitle = "Views",
                    icon = Icons.Default.Visibility,
                    iconTint = Color(0xFF27AE60),
                    onClick = { viewModel.navigateTo(Screen.PROFILE_VISITORS) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 3. Section Title: Recommended Matches
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Recommended Matches",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )
                    Text(
                        text = "Curated based on your partner preferences & community",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryMuted
                    )
                }

                TextButton(
                    onClick = { viewModel.navigateTo(Screen.FIND_MATCHES) },
                    modifier = Modifier.testTag("dashboard_view_all_matches_btn")
                ) {
                    Text("View All", fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                }
            }
        }

        // 4. Recommended Matches Cards List
        items(recommendedProfiles) { profile ->
            val match = viewModel.calculateMatch(profile)
            ProfileCard(
                profile = profile,
                matchResult = match,
                onViewProfile = { viewModel.openProfileDetail(profile) },
                onSendInterest = { viewModel.sendInterest(profile) },
                onToggleShortlist = { viewModel.toggleShortlist(profile) },
                onStartChat = { viewModel.openChatWithProfile(profile) }
            )
        }

        // 5. New Profiles Section
        item {
            Text(
                text = "New Profiles on Bondhon",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalMaroonPrimary,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Recently joined verified members looking for marriage",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryMuted
            )
        }

        items(newProfiles) { profile ->
            val match = viewModel.calculateMatch(profile)
            ProfileCard(
                profile = profile,
                matchResult = match,
                onViewProfile = { viewModel.openProfileDetail(profile) },
                onSendInterest = { viewModel.sendInterest(profile) },
                onToggleShortlist = { viewModel.toggleShortlist(profile) },
                onStartChat = { viewModel.openChatWithProfile(profile) }
            )
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    count: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = count,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextPrimaryDark
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = TextSecondaryMuted,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FindMatchesScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val filters by viewModel.filters.collectAsState()
    val profiles by viewModel.filteredProfiles.collectAsState()

    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        // Search & Filter Bar
        Surface(
            color = Color.White,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = filters.query,
                        onValueChange = { viewModel.updateFilters(filters.copy(query = it)) },
                        placeholder = { Text("Search by name, city, profession, religion...") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("search_query_input"),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = null, tint = TextSecondaryMuted)
                        },
                        trailingIcon = {
                            if (filters.query.isNotBlank()) {
                                IconButton(onClick = { viewModel.updateFilters(filters.copy(query = "")) }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { showFilterSheet = true },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(RoyalMaroonPrimary)
                            .testTag("open_filters_sheet_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filters",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Filter Chips row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("All", "Bride", "Groom").forEach { g ->
                        FilterChip(
                            selected = filters.gender == g,
                            onClick = { viewModel.updateFilters(filters.copy(gender = g)) },
                            label = { Text(g, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFFDE8EC),
                                selectedLabelColor = RoyalMaroonPrimary
                            )
                        )
                    }

                    FilterChip(
                        selected = filters.isVerifiedOnly,
                        onClick = { viewModel.updateFilters(filters.copy(isVerifiedOnly = !filters.isVerifiedOnly)) },
                        label = { Text("✓ Verified Only", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFFE8F5E9),
                            selectedLabelColor = Color(0xFF2E7D32)
                        )
                    )
                }

                // Results Count & Layout View Mode Switcher
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${profiles.size} Matches Found",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { viewModel.updateFilters(filters.copy(isGridView = false)) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewList,
                                contentDescription = "List View",
                                tint = if (!filters.isGridView) RoyalMaroonPrimary else TextSecondaryMuted
                            )
                        }
                        IconButton(
                            onClick = { viewModel.updateFilters(filters.copy(isGridView = true)) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.GridView,
                                contentDescription = "Grid View",
                                tint = if (filters.isGridView) RoyalMaroonPrimary else TextSecondaryMuted
                            )
                        }
                    }
                }
            }
        }

        // Match Results List or Empty State
        if (profiles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = TextSecondaryMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No matches found with selected filters",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimaryDark
                    )
                    Text(
                        text = "Try broadening your age range, location or community criteria.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondaryMuted,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.resetFilters() },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary)
                    ) {
                        Text("Reset Filters")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(profiles) { profile ->
                    val match = viewModel.calculateMatch(profile)
                    ProfileCard(
                        profile = profile,
                        matchResult = match,
                        onViewProfile = { viewModel.openProfileDetail(profile) },
                        onSendInterest = { viewModel.sendInterest(profile) },
                        onToggleShortlist = { viewModel.toggleShortlist(profile) },
                        onStartChat = { viewModel.openChatWithProfile(profile) }
                    )
                }
            }
        }
    }

    // Comprehensive Filter Modal Bottom Sheet
    if (showFilterSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Refine Matches (Filters)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )
                    TextButton(onClick = { viewModel.resetFilters() }) {
                        Text("Reset Filters", color = RoyalMaroonPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location filter
                Text("Select Location / City:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                val cities = listOf("All", "Kolkata", "Delhi", "Mumbai", "Bengaluru", "Hyderabad", "Pune", "Chennai", "Jaipur", "Lucknow", "Bhubaneswar")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    cities.forEach { c ->
                        FilterChip(
                            selected = filters.location == c,
                            onClick = { viewModel.updateFilters(filters.copy(location = c)) },
                            label = { Text(c, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Religion filter
                Text("Select Religion:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                val religions = listOf("All", "Hindu", "Muslim", "Sikh", "Christian", "Jain")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    religions.forEach { r ->
                        FilterChip(
                            selected = filters.religion == r,
                            onClick = { viewModel.updateFilters(filters.copy(religion = r)) },
                            label = { Text(r, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Diet filter
                Text("Food Preference:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                val diets = listOf("All", "Vegetarian", "Non-Vegetarian", "Eggetarian")
                FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    diets.forEach { d ->
                        FilterChip(
                            selected = filters.diet == d,
                            onClick = { viewModel.updateFilters(filters.copy(diet = d)) },
                            label = { Text(d, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showFilterSheet = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("apply_filters_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Search (${profiles.size} Results)", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
