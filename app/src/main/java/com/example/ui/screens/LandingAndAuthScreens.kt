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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.FilterCriteria
import com.example.ui.MatrimonyViewModel
import com.example.ui.Screen
import com.example.ui.components.BondhonLogo
import com.example.ui.theme.ErrorCrimson
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
fun LandingScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var quickGender by remember { mutableStateOf("Bride") }
    var quickMinAge by remember { mutableStateOf("22") }
    var quickMaxAge by remember { mutableStateOf("30") }
    var quickCity by remember { mutableStateOf("All Cities") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BondhonLogo(size = 36.dp, tagline = "A free matrimonial platform for meaningful relationships")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(
                    onClick = { viewModel.navigateTo(Screen.LOGIN) },
                    modifier = Modifier.testTag("landing_login_btn")
                ) {
                    Text(
                        text = "Login",
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )
                }
                Button(
                    onClick = { viewModel.navigateTo(Screen.REGISTER) },
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("landing_register_btn")
                ) {
                    Text(text = "Join 100% Free", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Banner Card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = RoyalMaroonPrimary),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(RoyalMaroonPrimary, RoyalMaroonDark)
                        )
                    )
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0x33FFFFFF),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccentLight)
                    ) {
                        Text(
                            text = "✦ 100% FREE MATRIMONIAL PLATFORM • NO SUBSCRIPTION FEES ✦",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccentLight,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Find Your Partner.\nBuild Your Bond.",
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Discover meaningful matrimonial connections with verified individuals who share your values, lifestyle and family vision. Completely free for all registered members.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFF7EBEB),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { viewModel.navigateTo(Screen.REGISTER) },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldAccent),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("hero_create_profile_btn")
                        ) {
                            Text(
                                text = "Create Your Free Profile",
                                color = RoyalMaroonDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { viewModel.navigateTo(Screen.FIND_MATCHES) },
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            modifier = Modifier.testTag("hero_explore_btn")
                        ) {
                            Text(text = "Browse Matches", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Match Search Box
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = RoyalMaroonPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Quick Match Finder",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "I am looking for a:",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Bride", "Groom").forEach { g ->
                        FilterChip(
                            selected = quickGender == g,
                            onClick = { quickGender = g },
                            label = { Text(g) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFFEE8EC),
                                selectedLabelColor = RoyalMaroonPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Age: $quickMinAge to $quickMaxAge yrs",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondaryMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("21-25", "25-30", "30-35").forEach { range ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFF3ECE2),
                                    modifier = Modifier.clickable {
                                        val parts = range.split("-")
                                        quickMinAge = parts[0]
                                        quickMaxAge = parts[1]
                                    }
                                ) {
                                    Text(
                                        text = range,
                                        fontSize = 11.sp,
                                        color = RoyalMaroonPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Location: $quickCity",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondaryMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            listOf("All", "Kolkata", "Delhi", "Bengaluru").forEach { c ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFF3ECE2),
                                    modifier = Modifier.clickable {
                                        quickCity = if (c == "All") "All Cities" else c
                                    }
                                ) {
                                    Text(
                                        text = c,
                                        fontSize = 11.sp,
                                        color = RoyalMaroonPrimary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val min = quickMinAge.toIntOrNull() ?: 21
                        val max = quickMaxAge.toIntOrNull() ?: 35
                        val loc = if (quickCity == "All Cities") "All" else quickCity
                        viewModel.updateFilters(
                            FilterCriteria(
                                gender = quickGender,
                                minAge = min,
                                maxAge = max,
                                location = loc
                            )
                        )
                        viewModel.navigateTo(Screen.FIND_MATCHES)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("quick_search_submit_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = GoldAccentLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Find Matches Now", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Trust Pillars
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Why Bondhon Matrimony?",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = RoyalMaroonPrimary
            )
            Text(
                text = "Dedicated to serious marriage, mutual respect, family values and 100% free access.",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondaryMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            val pillars = listOf(
                Triple(Icons.Default.VerifiedUser, "100% Free Forever", "No paid memberships, subscriptions or hidden paywalls for messaging or interests."),
                Triple(Icons.Default.FamilyRestroom, "Family & Lifestyle Harmony", "Deep compatibility based on community, values, education and life goals."),
                Triple(Icons.Default.Lock, "Total Privacy & Safety", "Photo privacy controls, contact protection and zero tolerance for casual dating."),
                Triple(Icons.Default.ThumbUp, "No Swipe Mechanics", "Carefully curated profile reviews designed for meaningful lifelong decisions.")
            )

            pillars.forEach { (icon, title, desc) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFDE8EC)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = RoyalMaroonPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryDark
                            )
                            Text(
                                text = desc,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryMuted
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Success Stories Highlight
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9EE)),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccentLight)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = RoyalMaroonPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Beautiful Bonds, Beautiful Beginnings",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = RoyalMaroonPrimary
                        )
                    }

                    TextButton(onClick = { viewModel.navigateTo(Screen.SUCCESS_STORIES) }) {
                        Text("View All", fontSize = 11.sp, color = RoyalMaroonPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“Bondhon helped us connect through shared cultural values and genuine family expectations. Today we are happily married.”",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = TextPrimaryDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "— Pooja Banerjee & Sourav Sen (Kolkata & Bengaluru)",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = GoldAccentDark
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var identifier by remember { mutableStateOf("amitav.sen@example.com") }
    var password by remember { mutableStateOf("password123") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BondhonLogo(size = 46.dp)

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Welcome Back to Bondhon Matrimony",
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = RoyalMaroonPrimary,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Log in with your registered Email or Mobile Number to access your matrimonial dashboard.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (errorMessage != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFFEBEE),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            fontSize = 12.sp,
                            color = ErrorCrimson,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = identifier,
                    onValueChange = {
                        identifier = it
                        errorMessage = null
                    },
                    label = { Text("Email Address or Mobile Number") },
                    placeholder = { Text("e.g. name@example.com or +919876543210") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_identifier_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = null
                    },
                    label = { Text("Password") },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("login_password_input"),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Remember Me & Forgot Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(checkedColor = RoyalMaroonPrimary)
                        )
                        Text("Remember Me", fontSize = 12.sp, color = TextPrimaryDark)
                    }

                    TextButton(
                        onClick = { viewModel.navigateTo(Screen.FORGOT_PASSWORD) },
                        modifier = Modifier.testTag("login_forgot_password_btn")
                    ) {
                        Text("Forgot Password?", fontSize = 12.sp, color = RoyalMaroonPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.login(identifier, password, rememberMe) { success, err ->
                            if (!success) {
                                errorMessage = err
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("login_submit_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Login", fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account?", fontSize = 12.sp, color = TextSecondaryMuted)
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(
                        onClick = { viewModel.navigateTo(Screen.REGISTER) },
                        modifier = Modifier.testTag("login_goto_register_btn")
                    ) {
                        Text("Create New Account", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Female") }
    var dob by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BondhonLogo(size = 46.dp)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Create Your Matrimonial Account",
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = RoyalMaroonPrimary
        )

        Text(
            text = "Registration is mandatory and 100% free for all members.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryMuted,
            modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (errorMessage != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFFEBEE),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            fontSize = 12.sp,
                            color = ErrorCrimson,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it; errorMessage = null },
                    label = { Text("Full Name *") },
                    placeholder = { Text("e.g. Priyanshu Sharma") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; errorMessage = null },
                    label = { Text("Email Address *") },
                    placeholder = { Text("e.g. yourname@example.com") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it; errorMessage = null },
                    label = { Text("Mobile Number *") },
                    placeholder = { Text("+91 9876543210") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Gender *", style = MaterialTheme.typography.labelSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Female", "Male").forEach { g ->
                        FilterChip(
                            selected = gender == g,
                            onClick = { gender = g },
                            label = { Text(g) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFFDE8EC),
                                selectedLabelColor = RoyalMaroonPrimary
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it; errorMessage = null },
                    label = { Text("Date of Birth *") },
                    placeholder = { Text("DD MMM YYYY, e.g. 15 Aug 1998") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it; errorMessage = null },
                    label = { Text("Password * (Min 6 characters)") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it; errorMessage = null },
                    label = { Text("Confirm Password *") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Terms checkbox
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isTermsAccepted,
                        onCheckedChange = { isTermsAccepted = it; errorMessage = null },
                        colors = CheckboxDefaults.colors(checkedColor = RoyalMaroonPrimary),
                        modifier = Modifier.testTag("register_terms_checkbox")
                    )
                    Text(
                        text = "I agree to the Terms of Service and Privacy Policy.",
                        fontSize = 11.sp,
                        color = TextPrimaryDark,
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.register(
                            fullName = fullName,
                            email = email,
                            mobileNumber = mobileNumber,
                            password = password,
                            confirmPassword = confirmPassword,
                            gender = gender,
                            dateOfBirth = dob,
                            isTermsAccepted = isTermsAccepted
                        ) { success, err ->
                            if (!success) {
                                errorMessage = err
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("register_create_account_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Create Free Account", fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Already registered?", fontSize = 12.sp, color = TextSecondaryMuted)
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = { viewModel.navigateTo(Screen.LOGIN) }) {
                        Text("Login here", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = RoyalMaroonPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    var identifier by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.navigateBack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = RoyalMaroonPrimary)
            }
            Text("Password Recovery", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = RoyalMaroonPrimary)
        }

        Spacer(modifier = Modifier.height(20.dp))

        BondhonLogo(size = 44.dp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Reset Your Password",
            style = MaterialTheme.typography.headlineSmall,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            color = RoyalMaroonPrimary
        )

        Text(
            text = "Enter your registered Email Address or Mobile Number. We will verify your account and provide secure password recovery instructions.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondaryMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                if (resultMessage != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSuccess) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = resultMessage ?: "",
                            fontSize = 12.sp,
                            color = if (isSuccess) SuccessGreen else ErrorCrimson,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                OutlinedTextField(
                    value = identifier,
                    onValueChange = { identifier = it },
                    label = { Text("Registered Email or Mobile") },
                    placeholder = { Text("e.g. name@example.com") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.requestPasswordReset(identifier) { success, msg ->
                            isSuccess = success
                            resultMessage = msg
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("forgot_password_submit_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Send Recovery Instructions", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    onClick = { viewModel.navigateTo(Screen.LOGIN) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Return to Login", color = RoyalMaroonPrimary)
                }
            }
        }
    }
}
