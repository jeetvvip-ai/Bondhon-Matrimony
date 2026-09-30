package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MatrimonyViewModel
import com.example.ui.Screen
import com.example.ui.theme.ErrorCrimson
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
fun SettingsScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val myProfile by viewModel.myProfile.collectAsState()
    val currentAccount by viewModel.currentAccount.collectAsState()

    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Summary Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "User Settings & Account",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )
                Text(
                    text = "Manage your login credentials, safety controls and account lifecycle.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryMuted,
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFAF4ED),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Logged in as: ${myProfile.fullName}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Email: ${currentAccount?.email ?: "amitav.sen@example.com"}", fontSize = 12.sp, color = TextSecondaryMuted)
                        Text("Mobile: ${currentAccount?.mobileNumber ?: "+91 9876543210"}", fontSize = 12.sp, color = TextSecondaryMuted)
                        Text("Plan: 100% Free Lifetime Matrimonial Access", fontSize = 11.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Account Actions
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                AccountActionRow(
                    icon = Icons.Default.Lock,
                    title = "Change Password",
                    onClick = { showChangePasswordDialog = true }
                )
                AccountActionRow(
                    icon = Icons.Default.Verified,
                    title = "Profile Verification Status",
                    onClick = { viewModel.navigateTo(Screen.VERIFICATION) }
                )
                AccountActionRow(
                    icon = Icons.Default.Person,
                    title = "Complete Your Profile (Wizard)",
                    onClick = { viewModel.navigateTo(Screen.PROFILE_WIZARD) }
                )
                AccountActionRow(
                    icon = Icons.Default.Security,
                    title = "Safety & Privacy Policy",
                    onClick = { viewModel.navigateTo(Screen.SAFETY_PRIVACY) }
                )
                AccountActionRow(
                    icon = Icons.Default.HelpOutline,
                    title = "About Us & Contact Support",
                    onClick = { viewModel.navigateTo(Screen.ABOUT_FAQ_CONTACT) }
                )
                AccountActionRow(
                    icon = Icons.Default.Logout,
                    title = "Logout from Bondhon",
                    onClick = { viewModel.logout() }
                )
                AccountActionRow(
                    icon = Icons.Default.Delete,
                    title = "Deactivate / Delete Account",
                    onClick = { showDeleteAccountDialog = true },
                    isDanger = true
                )
            }
        }
    }

    if (showChangePasswordDialog) {
        AlertDialog(
            onDismissRequest = { showChangePasswordDialog = false },
            title = { Text("Change Password") },
            text = {
                Column {
                    OutlinedTextField(
                        value = oldPassword,
                        onValueChange = { oldPassword = it },
                        label = { Text("Current Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password (min 6 chars)") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.changePassword(oldPassword, newPassword) { success, msg ->
                            showChangePasswordDialog = false
                            viewModel.myProfile.value // trigger
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary)
                ) {
                    Text("Update Password")
                }
            },
            dismissButton = {
                TextButton(onClick = { showChangePasswordDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showDeleteAccountDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteAccountDialog = false },
            title = { Text("Delete Your Account?") },
            text = { Text("Are you sure you want to permanently delete your Bondhon Matrimony account and profile? This action is irreversible.") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteAccountDialog = false
                        viewModel.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorCrimson)
                ) {
                    Text("Delete Account")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAccountDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun VerificationScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val myProfile by viewModel.myProfile.collectAsState()
    var selectedDocType by remember { mutableStateOf("Aadhaar Card") }
    var documentUploaded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Status Card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE3F2FD)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = VerifiedBlue,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Profile Verification Status",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = when (myProfile.verificationStatus) {
                        "Verified" -> Color(0xFFE8F5E9)
                        "Verification Pending" -> Color(0xFFFFF3E0)
                        else -> Color(0xFFFFEBEE)
                    },
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Text(
                        text = "Status: ${myProfile.verificationStatus}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (myProfile.verificationStatus) {
                            "Verified" -> SuccessGreen
                            "Verification Pending" -> Color(0xFFE65100)
                            else -> ErrorCrimson
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFF8E1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Important Note: Profile verification is not automatic. All documents are manually reviewed and approved by the Bondhon Trust & Safety Team.",
                        fontSize = 11.sp,
                        color = TextPrimaryDark,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        // Upload Form
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Submit Verification Documents",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Select Government ID Type:", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(6.dp))

                val docTypes = listOf("Aadhaar Card", "Passport", "Voter ID", "Driving License")
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    docTypes.take(2).forEach { dt ->
                        Button(
                            onClick = { selectedDocType = dt },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedDocType == dt) RoyalMaroonPrimary else Color(0xFFEFE8DF),
                                contentColor = if (selectedDocType == dt) Color.White else TextPrimaryDark
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(dt, fontSize = 11.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    docTypes.drop(2).forEach { dt ->
                        Button(
                            onClick = { selectedDocType = dt },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedDocType == dt) RoyalMaroonPrimary else Color(0xFFEFE8DF),
                                contentColor = if (selectedDocType == dt) Color.White else TextPrimaryDark
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(dt, fontSize = 11.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFAF5EE),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDFD1C4)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { documentUploaded = true }
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = if (documentUploaded) Icons.Default.CheckCircle else Icons.Default.UploadFile,
                            contentDescription = null,
                            tint = if (documentUploaded) SuccessGreen else RoyalMaroonPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (documentUploaded) "$selectedDocType Attached ✓" else "Tap to Select $selectedDocType (Front & Back)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimaryDark
                        )
                        Text("Supported: JPG, PNG, PDF (Max 5MB)", fontSize = 10.sp, color = TextSecondaryMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.requestVerification()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("submit_verification_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Submit for Verification Review", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SafetyPrivacyScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var isProfileHidden by remember { mutableStateOf(false) }
    var photoPrivacySetting by remember { mutableStateOf("Public") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Critical Fraud Warning Banner per requirement
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFFFF3E0),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFA000)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFE65100), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "IMPORTANT FRAUD & SAFETY ADVISORY",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB71C1C),
                        fontSize = 13.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "“Never share OTPs, passwords, banking information or send money to another member under any circumstances.”",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = TextPrimaryDark,
                    lineHeight = 18.sp
                )
                Text(
                    text = "Bondhon Matrimony is a free matrimonial service. Genuine matrimonial prospects and their families will never request monetary loans, gift charges, or bank transfers.",
                    fontSize = 11.sp,
                    color = TextSecondaryMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Privacy Controls
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Profile Privacy Controls",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Profile Visibility Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Hide Profile from Search", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        Text("Temporarily pause visibility in search while keeping active chat connections.", fontSize = 11.sp, color = TextSecondaryMuted)
                    }
                    Switch(
                        checked = isProfileHidden,
                        onCheckedChange = { isProfileHidden = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = RoyalMaroonPrimary)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Photo Privacy
                Text("Photo Privacy", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                Text("Control who can see your photos.", fontSize = 11.sp, color = TextSecondaryMuted)
                Spacer(modifier = Modifier.height(6.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Public", "Private").forEach { pp ->
                        Button(
                            onClick = { photoPrivacySetting = pp },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (photoPrivacySetting == pp) RoyalMaroonPrimary else Color(0xFFF3EDE6),
                                contentColor = if (photoPrivacySetting == pp) Color.White else TextPrimaryDark
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (pp == "Public") "Public to Members" else "Private (On Request)", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccountActionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit,
    isDanger: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isDanger) ErrorCrimson else RoyalMaroonPrimary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (isDanger) ErrorCrimson else TextPrimaryDark
        )
    }
}
