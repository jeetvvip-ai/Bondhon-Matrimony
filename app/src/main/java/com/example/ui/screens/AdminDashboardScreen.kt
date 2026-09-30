package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MatrimonyViewModel
import com.example.ui.components.ProfileAvatar
import com.example.ui.theme.ErrorCrimson
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
fun AdminDashboardScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    var adminTab by remember { mutableIntStateOf(0) }
    val allProfiles by viewModel.allProfiles.collectAsState()
    val reports by viewModel.reports.collectAsState()
    val interests by viewModel.interests.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    val totalUsers = allProfiles.size
    val maleUsers = allProfiles.count { it.gender == "Male" }
    val femaleUsers = allProfiles.count { it.gender == "Female" }
    val verifiedUsers = allProfiles.count { it.verificationStatus == "Verified" }
    val pendingVerifications = allProfiles.count { it.verificationStatus == "Verification Pending" }
    val reportedProfiles = reports.size
    val activeUsers = allProfiles.count { it.accountStatus == "Active" }
    val totalConnections = interests.count { it.status == "ACCEPTED" }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        // Admin Top Banner
        Surface(
            color = Color(0xFF3B0011),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AdminPanelSettings,
                        contentDescription = null,
                        tint = GoldAccentLight,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Bondhon Matrimony • Admin Central",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Trust, Profile Moderation & Member Safety",
                            color = Color(0xFFF7CCD3),
                            fontSize = 10.sp
                        )
                    }
                }

                Button(
                    onClick = { viewModel.navigateBack() },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Exit Admin", fontSize = 11.sp)
                }
            }
        }

        // Tabs
        TabRow(
            selectedTabIndex = adminTab,
            containerColor = Color.White,
            contentColor = RoyalMaroonPrimary
        ) {
            Tab(selected = adminTab == 0, onClick = { adminTab = 0 }, text = { Text("Overview", fontSize = 12.sp, fontWeight = FontWeight.Bold) })
            Tab(selected = adminTab == 1, onClick = { adminTab = 1 }, text = { Text("Users ($totalUsers)", fontSize = 12.sp, fontWeight = FontWeight.Bold) })
            Tab(selected = adminTab == 2, onClick = { adminTab = 2 }, text = { Text("Verifications ($pendingVerifications)", fontSize = 12.sp, fontWeight = FontWeight.Bold) })
            Tab(selected = adminTab == 3, onClick = { adminTab = 3 }, text = { Text("Reports ($reportedProfiles)", fontSize = 12.sp, fontWeight = FontWeight.Bold) })
        }

        when (adminTab) {
            0 -> {
                // Overview & KPI Metrics
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Platform Statistics (100% Free Service)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminStatCard("Total Members", "$totalUsers", "Registered", RoyalMaroonPrimary, modifier = Modifier.weight(1f))
                        AdminStatCard("Active Users", "$activeUsers", "Good Standing", SuccessGreen, modifier = Modifier.weight(1f))
                        AdminStatCard("Verified", "$verifiedUsers", "ID Approved", VerifiedBlue, modifier = Modifier.weight(1f))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminStatCard("Grooms (Male)", "$maleUsers", "Ratio", Color(0xFF1976D2), modifier = Modifier.weight(1f))
                        AdminStatCard("Brides (Female)", "$femaleUsers", "Ratio", Color(0xFFC2185B), modifier = Modifier.weight(1f))
                        AdminStatCard("Pending Verif.", "$pendingVerifications", "In Queue", Color(0xFFE65100), modifier = Modifier.weight(1f))
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AdminStatCard("Reports", "$reportedProfiles", "Trust Flags", ErrorCrimson, modifier = Modifier.weight(1f))
                        AdminStatCard("Connections", "$totalConnections", "Mutual Matches", GoldAccentDark, modifier = Modifier.weight(1f))
                        AdminStatCard("Membership", "Free", "No Paywalls", RoyalMaroonDark, modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Registration chart
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.BarChart, contentDescription = null, tint = RoyalMaroonPrimary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("New Registrations (Last 7 Days)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                val daily = listOf("Thu" to 4, "Fri" to 7, "Sat" to 12, "Sun" to 15, "Mon" to 8, "Tue" to 10, "Today" to 6)
                                val max = 15f
                                daily.forEach { (day, count) ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("$count", fontSize = 10.sp, color = RoyalMaroonPrimary, fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(22.dp)
                                                .height((75 * (count / max)).dp)
                                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                                .background(RoyalMaroonPrimary)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(day, fontSize = 9.sp, color = TextSecondaryMuted)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // User Management
                val filteredUsers = allProfiles.filter {
                    searchQuery.isBlank() ||
                            it.fullName.contains(searchQuery, true) ||
                            it.id.contains(searchQuery, true) ||
                            it.city.contains(searchQuery, true)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search by name, ID or city...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(filteredUsers) { user ->
                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        ProfileAvatar(name = user.fullName, seed = user.avatarSeed, bgColorHex = user.avatarBgColorHex, size = 44.dp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(user.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text("(${user.id})", fontSize = 11.sp, color = TextSecondaryMuted)
                                            }
                                            Text("${user.gender} • ${user.age} yrs • ${user.city}", fontSize = 11.sp, color = TextSecondaryMuted)
                                            Text("Status: ${user.accountStatus} • ${user.verificationStatus}", fontSize = 10.sp, color = RoyalMaroonPrimary)
                                        }

                                        IconButton(onClick = { viewModel.adminDeleteProfile(user.id) }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorCrimson, modifier = Modifier.size(18.dp))
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        if (user.verificationStatus != "Verified") {
                                            Button(
                                                onClick = { viewModel.adminApproveVerification(user.id) },
                                                colors = ButtonDefaults.buttonColors(containerColor = VerifiedBlue),
                                                shape = RoundedCornerShape(6.dp),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text("Verify ✓", fontSize = 10.sp)
                                            }
                                        }

                                        if (user.accountStatus == "Active") {
                                            OutlinedButton(
                                                onClick = { viewModel.adminUpdateAccountStatus(user.id, "Suspended") },
                                                shape = RoundedCornerShape(6.dp),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text("Suspend", fontSize = 10.sp)
                                            }
                                            OutlinedButton(
                                                onClick = { viewModel.adminUpdateAccountStatus(user.id, "Blocked") },
                                                shape = RoundedCornerShape(6.dp),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text("Block", fontSize = 10.sp, color = ErrorCrimson)
                                            }
                                        } else {
                                            Button(
                                                onClick = { viewModel.adminUpdateAccountStatus(user.id, "Active") },
                                                colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                                                shape = RoundedCornerShape(6.dp),
                                                modifier = Modifier.weight(1f)
                                            ) {
                                                Text("Restore", fontSize = 10.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // Verification Queue
                val pendingList = allProfiles.filter { it.verificationStatus == "Verification Pending" || it.verificationStatus == "Not Verified" }

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pendingList) { profile ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    ProfileAvatar(name = profile.fullName, seed = profile.avatarSeed, bgColorHex = profile.avatarBgColorHex, size = 46.dp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(profile.fullName, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text("${profile.city}, ${profile.state} • ${profile.highestQualification}", fontSize = 11.sp, color = TextSecondaryMuted)
                                        Text("Submitted: Govt ID / Identity Proof", fontSize = 11.sp, color = GoldAccentDark)
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.adminApproveVerification(profile.id) },
                                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Approve Verified Badge", fontSize = 11.sp)
                                    }

                                    OutlinedButton(
                                        onClick = { viewModel.adminRejectVerification(profile.id) },
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Reject", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            3 -> {
                // Reports Management
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reports) { report ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Warning, contentDescription = null, tint = ErrorCrimson, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(report.reason, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = ErrorCrimson)
                                    }
                                    Text(report.status, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = RoyalMaroonPrimary)
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text("Reported Member: ${report.reportedUserName} (${report.reportedUserId})", fontWeight = FontWeight.Medium, fontSize = 12.sp)
                                Text("Reported By: ${report.reportedByName} • Date: ${report.date}", fontSize = 11.sp, color = TextSecondaryMuted)

                                if (report.details.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFFFAF5EE),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 6.dp)
                                    ) {
                                        Text(report.details, fontSize = 11.sp, color = TextPrimaryDark, modifier = Modifier.padding(8.dp))
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Button(
                                        onClick = { viewModel.adminUpdateReportStatus(report.id, "Warned") },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Warn User", fontSize = 10.sp)
                                    }
                                    Button(
                                        onClick = { viewModel.adminUpdateReportStatus(report.id, "Suspended") },
                                        colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Suspend", fontSize = 10.sp)
                                    }
                                    OutlinedButton(
                                        onClick = { viewModel.adminUpdateReportStatus(report.id, "Dismissed") },
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Dismiss", fontSize = 10.sp)
                                    }
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
fun AdminStatCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(title, fontSize = 10.sp, color = TextSecondaryMuted, maxLines = 1)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
            Text(subtitle, fontSize = 9.sp, color = TextSecondaryMuted, maxLines = 1)
        }
    }
}

