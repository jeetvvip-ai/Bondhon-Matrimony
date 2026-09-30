package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.model.PartnerPreferences
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileWizardScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 8

    // Form fields
    var fullName by remember { mutableStateOf(viewModel.myProfile.value.fullName) }
    var dob by remember { mutableStateOf(viewModel.myProfile.value.dateOfBirth.ifBlank { "12 Jul 1998" }) }
    var gender by remember { mutableStateOf(viewModel.myProfile.value.gender) }
    var height by remember { mutableStateOf("5 ft 10 in (178 cm)") }
    var religion by remember { mutableStateOf("Hindu") }
    var community by remember { mutableStateOf("Kayastha") }
    var motherTongue by remember { mutableStateOf("Bengali") }
    var maritalStatus by remember { mutableStateOf("Never Married") }
    var city by remember { mutableStateOf("Kolkata") }
    var state by remember { mutableStateOf("West Bengal") }
    var country by remember { mutableStateOf("India") }

    var highestQualification by remember { mutableStateOf("B.Tech & M.S. in Computer Science") }
    var college by remember { mutableStateOf("Jadavpur University") }
    var fieldOfStudy by remember { mutableStateOf("Computer Engineering") }
    var profession by remember { mutableStateOf("Staff Software Architect") }
    var company by remember { mutableStateOf("Enterprise Cloud Platforms") }
    var workLocation by remember { mutableStateOf("Kolkata & Remote") }
    var annualIncome by remember { mutableStateOf("₹20–30 Lakh") }

    var fatherOccupation by remember { mutableStateOf("Senior Professor of Economics (Retd)") }
    var motherOccupation by remember { mutableStateOf("Homemaker & Writer") }
    var brothers by remember { mutableStateOf("1") }
    var sisters by remember { mutableStateOf("0") }
    var familyType by remember { mutableStateOf("Nuclear") }
    var familyValues by remember { mutableStateOf("Moderate") }
    var familyLocation by remember { mutableStateOf("Salt Lake, Kolkata") }

    var diet by remember { mutableStateOf("Non-Vegetarian") }
    var smoking by remember { mutableStateOf("No") }
    var drinking by remember { mutableStateOf("No") }
    var exercise by remember { mutableStateOf("Regular Swimming & Cycling") }
    var hobbies by remember { mutableStateOf("Reading Classic Literature, Photography, Road Trips") }
    var interests by remember { mutableStateOf("Literature, Technology, Heritage Travel") }
    var languagesKnown by remember { mutableStateOf("Bengali, English, Hindi") }
    var aboutMe by remember {
        mutableStateOf("A grounded, forward-looking person who values cultural heritage and close-knit family relationships. Looking for an understanding partner who shares an open mindset and a passion for lifelong growth.")
    }

    var partnerMinAge by remember { mutableStateOf("23") }
    var partnerMaxAge by remember { mutableStateOf("30") }
    var partnerLocation by remember { mutableStateOf("Any") }
    var partnerReligion by remember { mutableStateOf("Any") }
    var partnerCommunity by remember { mutableStateOf("Any") }
    var partnerTongue by remember { mutableStateOf("Any") }
    var partnerEducation by remember { mutableStateOf("Any") }
    var partnerIncome by remember { mutableStateOf("Any") }

    var photoPrivacy by remember { mutableStateOf("Public") }
    var additionalPhotosCount by remember { mutableIntStateOf(3) }
    var isPublished by remember { mutableStateOf(false) }

    val progressPercent = ((currentStep.toFloat() / totalSteps.toFloat()) * 100).toInt()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Wizard Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (currentStep > 1) currentStep-- else viewModel.navigateBack()
                },
                modifier = Modifier.testTag("wizard_back_btn")
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = RoyalMaroonPrimary)
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Complete Your Matrimonial Profile",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )
                Text(
                    text = "Step $currentStep of $totalSteps • $progressPercent% Completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Progress Indicator
        LinearProgressIndicator(
            progress = { currentStep.toFloat() / totalSteps.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = RoyalMaroonPrimary,
            trackColor = Color(0xFFEADBCE)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isPublished) {
            // Success Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F5E9)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(36.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Your profile is now live!",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )

                    Text(
                        text = "Congratulations! Your profile on Bondhon Matrimony is published and visible to prospective matrimonial matches. All features are 100% free.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
                    )

                    Button(
                        onClick = { viewModel.navigateTo(Screen.DASHBOARD) },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("wizard_go_dashboard_btn")
                    ) {
                        Text("Go to Member Dashboard", fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            // Form Steps
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    when (currentStep) {
                        1 -> {
                            // Step 1: Basic Information
                            Text("Step 1: Basic Information", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("Personal identity, origin and demographics", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name *") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth *") }, modifier = Modifier.weight(1f), singleLine = true)
                                OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height *") }, modifier = Modifier.weight(1f), singleLine = true)
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = religion, onValueChange = { religion = it }, label = { Text("Religion *") }, modifier = Modifier.weight(1f), singleLine = true)
                                OutlinedTextField(value = community, onValueChange = { community = it }, label = { Text("Community / Caste") }, modifier = Modifier.weight(1f), singleLine = true)
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = motherTongue, onValueChange = { motherTongue = it }, label = { Text("Mother Tongue *") }, modifier = Modifier.weight(1f), singleLine = true)
                                OutlinedTextField(value = maritalStatus, onValueChange = { maritalStatus = it }, label = { Text("Marital Status *") }, modifier = Modifier.weight(1f), singleLine = true)
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City *") }, modifier = Modifier.weight(1f), singleLine = true)
                                OutlinedTextField(value = state, onValueChange = { state = it }, label = { Text("State *") }, modifier = Modifier.weight(1f), singleLine = true)
                            }
                        }

                        2 -> {
                            // Step 2: Education & Career
                            Text("Step 2: Education & Career", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("Academic qualifications and professional livelihood", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(value = highestQualification, onValueChange = { highestQualification = it }, label = { Text("Highest Qualification *") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = college, onValueChange = { college = it }, label = { Text("College / University *") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = fieldOfStudy, onValueChange = { fieldOfStudy = it }, label = { Text("Field of Study") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = profession, onValueChange = { profession = it }, label = { Text("Profession / Designation *") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = company, onValueChange = { company = it }, label = { Text("Company Name / Sector") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = workLocation, onValueChange = { workLocation = it }, label = { Text("Work Location (City)") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            Text("Annual Income Range:", style = MaterialTheme.typography.labelMedium)
                            val incomes = listOf("Not Specified", "₹1–3 Lakh", "₹3–5 Lakh", "₹5–10 Lakh", "₹10–20 Lakh", "₹20–30 Lakh", "₹30 Lakh+")
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                incomes.forEach { inc ->
                                    FilterChip(
                                        selected = annualIncome == inc,
                                        onClick = { annualIncome = inc },
                                        label = { Text(inc, fontSize = 11.sp) }
                                    )
                                }
                            }
                        }

                        3 -> {
                            // Step 3: Family Details
                            Text("Step 3: Family Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("Family roots, parents and siblings", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(value = fatherOccupation, onValueChange = { fatherOccupation = it }, label = { Text("Father's Occupation *") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = motherOccupation, onValueChange = { motherOccupation = it }, label = { Text("Mother's Occupation *") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = brothers, onValueChange = { brothers = it }, label = { Text("Number of Brothers") }, modifier = Modifier.weight(1f))
                                OutlinedTextField(value = sisters, onValueChange = { sisters = it }, label = { Text("Number of Sisters") }, modifier = Modifier.weight(1f))
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Text("Family Type:", style = MaterialTheme.typography.labelSmall)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("Nuclear", "Joint").forEach { ft ->
                                    FilterChip(selected = familyType == ft, onClick = { familyType = ft }, label = { Text(ft) })
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))

                            Text("Family Values:", style = MaterialTheme.typography.labelSmall)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf("Traditional", "Moderate", "Liberal").forEach { fv ->
                                    FilterChip(selected = familyValues == fv, onClick = { familyValues = fv }, label = { Text(fv) })
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = familyLocation, onValueChange = { familyLocation = it }, label = { Text("Family Native / Home City") }, modifier = Modifier.fillMaxWidth())
                        }

                        4 -> {
                            // Step 4: Lifestyle
                            Text("Step 4: Lifestyle & Habits", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("Food, fitness, hobbies and languages", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Food Preference:", style = MaterialTheme.typography.labelSmall)
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                listOf("Vegetarian", "Non-Vegetarian", "Eggetarian", "Jain").forEach { fp ->
                                    FilterChip(selected = diet == fp, onClick = { diet = fp }, label = { Text(fp) })
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = smoking, onValueChange = { smoking = it }, label = { Text("Smoking") }, modifier = Modifier.weight(1f))
                                OutlinedTextField(value = drinking, onValueChange = { drinking = it }, label = { Text("Drinking") }, modifier = Modifier.weight(1f))
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = exercise, onValueChange = { exercise = it }, label = { Text("Exercise / Fitness Routine") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = hobbies, onValueChange = { hobbies = it }, label = { Text("Hobbies") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = languagesKnown, onValueChange = { languagesKnown = it }, label = { Text("Languages Known") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(12.dp))

                            Text("About Me:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            OutlinedTextField(
                                value = aboutMe,
                                onValueChange = { if (it.length <= 500) aboutMe = it },
                                label = { Text("Free text description about yourself, family and life vision...") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                                maxLines = 5
                            )
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Text("${aboutMe.length}/500 characters", fontSize = 11.sp, color = TextSecondaryMuted)
                            }
                        }

                        5 -> {
                            // Step 5: Partner Preferences
                            Text("Step 5: Partner Preferences", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("All preferences are optional. Leave as 'Any' if flexible.", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(value = partnerMinAge, onValueChange = { partnerMinAge = it }, label = { Text("Preferred Min Age") }, modifier = Modifier.weight(1f))
                                OutlinedTextField(value = partnerMaxAge, onValueChange = { partnerMaxAge = it }, label = { Text("Preferred Max Age") }, modifier = Modifier.weight(1f))
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = partnerLocation, onValueChange = { partnerLocation = it }, label = { Text("Preferred Location (e.g. Any, Kolkata, Delhi)") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = partnerReligion, onValueChange = { partnerReligion = it }, label = { Text("Preferred Religion") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = partnerCommunity, onValueChange = { partnerCommunity = it }, label = { Text("Preferred Community") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = partnerEducation, onValueChange = { partnerEducation = it }, label = { Text("Preferred Education") }, modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(value = partnerIncome, onValueChange = { partnerIncome = it }, label = { Text("Preferred Income") }, modifier = Modifier.fillMaxWidth())
                        }

                        6 -> {
                            // Step 6: Photos
                            Text("Step 6: Photographs & Privacy", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("Manage profile photo and gallery (secured storage)", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(12.dp))

                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ProfileAvatar(
                                    name = fullName,
                                    seed = "AS",
                                    bgColorHex = 0xFF7A1228,
                                    size = 84.dp,
                                    isVerified = false
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedButton(onClick = { /* simulated pick */ }) {
                                        Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Change Avatar")
                                    }
                                    OutlinedButton(onClick = { additionalPhotosCount++ }) {
                                        Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Add Photo ($additionalPhotosCount)")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Photo Privacy Setting:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Public", "Private").forEach { pp ->
                                    FilterChip(
                                        selected = photoPrivacy == pp,
                                        onClick = { photoPrivacy = pp },
                                        label = { Text(if (pp == "Public") "Public to Registered Members" else "Private (On Request Only)") }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFFF8E1),
                                border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccentLight),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = GoldAccentDark, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Photo Guidelines: Please upload clear, modest and family-appropriate photographs. Never upload private or sensitive images. Inappropriate photos will be removed and accounts suspended.",
                                        fontSize = 11.sp,
                                        color = TextPrimaryDark,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }

                        7 -> {
                            // Step 7: Profile Preview
                            Text("Step 7: Profile Preview", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Text("Review your matrimonial card exactly as other prospective families see it", style = MaterialTheme.typography.bodySmall, color = TextSecondaryMuted)
                            Spacer(modifier = Modifier.height(14.dp))

                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5EE))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        ProfileAvatar(name = fullName, seed = "AS", bgColorHex = 0xFF7A1228, size = 56.dp, isVerified = false)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(fullName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RoyalMaroonPrimary)
                                            Text("$dob • $height • $city", fontSize = 12.sp, color = TextSecondaryMuted)
                                            Text("Free Member • Not Verified Yet", fontSize = 11.sp, color = GoldAccentDark)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("Career: $profession at $company ($annualIncome)", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    Text("Education: $highestQualification, $college", fontSize = 12.sp)
                                    Text("Family: Father - $fatherOccupation, Mother - $motherOccupation", fontSize = 12.sp)
                                    Text("Diet & Habits: $diet • Non-Smoker", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("About Me: $aboutMe", fontSize = 11.sp, color = TextSecondaryMuted, maxLines = 3)
                                }
                            }
                        }

                        8 -> {
                            // Step 8: Publish Profile
                            Text("Step 8: Publish Matrimonial Profile", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                            Spacer(modifier = Modifier.height(10.dp))

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFE8F5E9),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Ready for Public Matrimonial Search", fontWeight = FontWeight.Bold, color = SuccessGreen)
                                    }
                                    Text(
                                        text = "Publishing makes your profile visible in search results for prospective brides, grooms and families looking for marriage.",
                                        fontSize = 12.sp,
                                        color = TextPrimaryDark,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Platform Declaration:\nI solemnly declare that the information provided is accurate and entered for sincere matrimonial purposes.",
                                fontSize = 11.sp,
                                color = TextSecondaryMuted,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Wizard Navigation Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentStep > 1) {
                            OutlinedButton(
                                onClick = { currentStep-- },
                                modifier = Modifier.testTag("wizard_prev_step_btn")
                            ) {
                                Text("Previous")
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        if (currentStep < totalSteps) {
                            Button(
                                onClick = { currentStep++ },
                                colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                                modifier = Modifier.testTag("wizard_next_step_btn")
                            ) {
                                Text("Next Step")
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                        } else {
                            Button(
                                onClick = {
                                    val updatedProfile = MatrimonialProfile(
                                        id = viewModel.myProfile.value.id,
                                        userId = viewModel.myProfile.value.id,
                                        fullName = fullName,
                                        gender = gender,
                                        age = 28,
                                        dateOfBirth = dob,
                                        height = height,
                                        heightInches = 70,
                                        religion = religion,
                                        community = community,
                                        motherTongue = motherTongue,
                                        maritalStatus = maritalStatus,
                                        city = city,
                                        state = state,
                                        country = country,
                                        highestQualification = highestQualification,
                                        college = college,
                                        fieldOfStudy = fieldOfStudy,
                                        profession = profession,
                                        company = company,
                                        workLocation = workLocation,
                                        annualIncome = annualIncome,
                                        fatherOccupation = fatherOccupation,
                                        motherOccupation = motherOccupation,
                                        brothers = brothers.toIntOrNull() ?: 1,
                                        sisters = sisters.toIntOrNull() ?: 0,
                                        familyType = familyType,
                                        familyValues = familyValues,
                                        familyLocation = familyLocation,
                                        diet = diet,
                                        smoking = smoking,
                                        drinking = drinking,
                                        exercise = exercise,
                                        hobbies = hobbies,
                                        interests = interests,
                                        languagesKnown = languagesKnown,
                                        aboutMe = aboutMe,
                                        avatarSeed = "AS",
                                        avatarBgColorHex = 0xFF7A1228,
                                        photoPrivacy = photoPrivacy,
                                        verificationStatus = viewModel.myProfile.value.verificationStatus,
                                        isPublished = true
                                    )
                                    viewModel.saveMyProfile(updatedProfile)

                                    val prefs = PartnerPreferences(
                                        id = viewModel.myProfile.value.id,
                                        preferredAgeMin = partnerMinAge.toIntOrNull() ?: 21,
                                        preferredAgeMax = partnerMaxAge.toIntOrNull() ?: 35,
                                        preferredLocation = partnerLocation,
                                        preferredReligion = partnerReligion,
                                        preferredCommunity = partnerCommunity,
                                        preferredMotherTongue = partnerTongue,
                                        preferredEducation = partnerEducation,
                                        preferredIncome = partnerIncome
                                    )
                                    viewModel.savePreferences(prefs)

                                    isPublished = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                                modifier = Modifier.testTag("wizard_publish_profile_btn")
                            ) {
                                Text("Publish Profile ✓", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
