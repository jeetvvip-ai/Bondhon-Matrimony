package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.example.data.model.SuccessStory
import com.example.ui.MatrimonyViewModel
import com.example.ui.components.BondhonLogo
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldAccentDark
import com.example.ui.theme.GoldAccentLight
import com.example.ui.theme.RoyalMaroonDark
import com.example.ui.theme.RoyalMaroonPrimary
import com.example.ui.theme.TextPrimaryDark
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarmCreamBackground

@Composable
fun SuccessStoriesScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    val stories = viewModel.getSuccessStories()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCreamBackground)
    ) {
        // Top Header
        Surface(
            color = Color.White,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Beautiful Bonds, Beautiful Beginnings",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = RoyalMaroonPrimary
                )
                Text(
                    text = "Read how verified brides, grooms and their families discovered their lifelong companions on Bondhon Matrimony.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryMuted,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Mandatory disclaimer per requirement
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFF8E1),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Disclaimer: Sample fictional representations for prototype demonstration. To be replaced with permissioned customer stories in live production.",
                        fontSize = 10.sp,
                        color = TextSecondaryMuted,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(stories) { story ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFDE8EC)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = RoyalMaroonPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "${story.brideName} & ${story.groomName}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = RoyalMaroonPrimary
                                )
                                Text(
                                    text = "${story.weddingYear} • ${story.location}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondaryMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFAF5EE),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = story.quote,
                                style = MaterialTheme.typography.bodyMedium,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = TextPrimaryDark,
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = story.story,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextPrimaryDark,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AboutFaqContactScreen(
    viewModel: MatrimonyViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val scrollState = rememberScrollState()

    var contactName by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var contactSubject by remember { mutableStateOf("") }
    var contactMessage by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

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
            Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("About Us", fontWeight = FontWeight.Bold) })
            Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("FAQ", fontWeight = FontWeight.Bold) })
            Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Contact Support", fontWeight = FontWeight.Bold) })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // About Us
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            BondhonLogo(size = 40.dp)
                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "About Bondhon Matrimony",
                                style = MaterialTheme.typography.titleLarge,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = RoyalMaroonPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "“Bondhon” signifies the sacred and cherished bond uniting two individuals and their families in lifelong marriage.",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = TextPrimaryDark
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Our Mission:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccentDark
                            )
                            Text(
                                text = "“To help individuals and families discover meaningful matrimonial connections through trust, compatibility and modern technology.”",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextPrimaryDark,
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Our Core Values:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccentDark
                            )
                            Text(
                                text = "• Dignity & Family Values: Respecting cultural heritage and parental guidance.\n• Security & Zero Dating Mechanics: Serious marriage intention only; no casual swiping.\n• Transparency & Verified Credentials: Validated educational and identity records.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextPrimaryDark,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                1 -> {
                    // FAQ Accordion
                    Text(
                        text = "Frequently Asked Questions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = RoyalMaroonPrimary
                    )

                    val faqs = listOf(
                        "How does Bondhon Matrimony work?" to "Bondhon connects prospective brides, grooms and families through curated compatibility metrics. You can search, filter, express matrimonial interest, and communicate once mutual connection is established.",
                        "How do I create a profile?" to "Use our 7-step Profile Creation Wizard to enter your basic details, education, career, family background, lifestyle habits, partner preferences, and upload modest photographs.",
                        "How can I send interest?" to "Simply tap 'Send Interest' on any profile. The receiver will be notified and can choose to Accept, Decline or save to Maybe Later.",
                        "When can I message someone?" to "To safeguard privacy, direct real-time messaging is unlocked only after both users have mutually accepted matrimonial interest.",
                        "How does profile verification work?" to "You upload an authentic government ID (e.g. Aadhaar, Passport, Voter ID). Our Trust Team verifies credentials and grants the official Blue Verified Badge.",
                        "How do I report a profile?" to "Tap 'Report Profile' on any profile card or details page. Select the reason (e.g. Fake Profile, Financial Request) and our safety team reviews it within 24 hours.",
                        "How can I cancel membership?" to "You can manage or cancel your membership at any time from your Account Settings without hidden cancellation fees.",
                        "How is my personal information protected?" to "We never share phone numbers or confidential details publicly. Photo privacy settings allow you to restrict photos to Members Only or Request to View."
                    )

                    faqs.forEach { (q, a) ->
                        FaqItem(question = q, answer = a)
                    }
                }

                2 -> {
                    // Contact Us Form
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "Contact Bondhon Matrimony",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = RoyalMaroonPrimary
                            )
                            Text(
                                text = "We are here to assist your matrimonial journey with prompt family support.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondaryMuted,
                                modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
                            )

                            if (isSubmitted) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFE8F5E9),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text("Inquiry Submitted Successfully!", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                        Text("Our relationship manager will get in touch with you shortly.", fontSize = 12.sp, color = TextPrimaryDark)
                                    }
                                }
                            } else {
                                OutlinedTextField(value = contactName, onValueChange = { contactName = it }, label = { Text("Your Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(value = contactEmail, onValueChange = { contactEmail = it }, label = { Text("Email Address") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(value = contactPhone, onValueChange = { contactPhone = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(value = contactSubject, onValueChange = { contactSubject = it }, label = { Text("Subject") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(value = contactMessage, onValueChange = { contactMessage = it }, label = { Text("Your Message / Question") }, modifier = Modifier.fillMaxWidth(), maxLines = 4)
                                Spacer(modifier = Modifier.height(14.dp))

                                Button(
                                    onClick = { isSubmitted = true },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp)
                                        .testTag("submit_contact_btn"),
                                    colors = ButtonDefaults.buttonColors(containerColor = RoyalMaroonPrimary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Submit Inquiry")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Contact info
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFFAF5EE),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Call, contentDescription = null, tint = RoyalMaroonPrimary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Toll Free Support: 1800-120-BONDHON", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Email, contentDescription = null, tint = RoyalMaroonPrimary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Support Email: care@bondhonmatrimony.com", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Schedule, contentDescription = null, tint = RoyalMaroonPrimary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Support Hours: 9:00 AM – 8:00 PM IST (Mon–Sat)", fontSize = 12.sp)
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
fun FaqItem(question: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryDark,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = RoyalMaroonPrimary
                )
            }

            AnimatedVisibility(visible = expanded) {
                Text(
                    text = answer,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryMuted,
                    lineHeight = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
