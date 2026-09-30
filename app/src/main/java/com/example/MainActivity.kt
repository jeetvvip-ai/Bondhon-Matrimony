package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MatrimonyViewModel
import com.example.ui.Screen
import com.example.ui.components.BondhonAppBar
import com.example.ui.components.BondhonBottomBar
import com.example.ui.screens.AboutFaqContactScreen
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.ChatDetailScreen
import com.example.ui.screens.ConversationListScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DetailedProfileScreen
import com.example.ui.screens.FindMatchesScreen
import com.example.ui.screens.FirebaseGuideScreen
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.InterestsScreen
import com.example.ui.screens.LandingScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.ProfileVisitorsScreen
import com.example.ui.screens.ProfileWizardScreen
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.SafetyPrivacyScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ShortlistScreen
import com.example.ui.screens.SuccessStoriesScreen
import com.example.ui.screens.VerificationScreen
import com.example.ui.theme.BondhonMatrimonyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BondhonMatrimonyTheme {
                BondhonMatrimonyApp()
            }
        }
    }
}

@Composable
fun BondhonMatrimonyApp(
    viewModel: MatrimonyViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isAdmin by viewModel.isAdmin.collectAsState()
    val selectedProfile by viewModel.selectedProfile.collectAsState()
    val selectedConversation by viewModel.selectedConversation.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val snackBarMessage by viewModel.snackBarMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val unreadNotifications = notifications.count { !it.isRead }

    LaunchedEffect(snackBarMessage) {
        snackBarMessage?.let { msg ->
            snackbarHostState.showSnackbar(
                message = msg,
                duration = SnackbarDuration.Short
            )
            viewModel.clearSnackBar()
        }
    }

    // BackHandler for smooth backstack navigation
    val canGoBack = currentScreen != Screen.DASHBOARD && currentScreen != Screen.LANDING
    BackHandler(enabled = canGoBack) {
        viewModel.navigateBack()
    }

    val showTopBar = currentScreen != Screen.LANDING &&
            currentScreen != Screen.LOGIN &&
            currentScreen != Screen.REGISTER &&
            currentScreen != Screen.FORGOT_PASSWORD &&
            currentScreen != Screen.CHAT_DETAIL &&
            currentScreen != Screen.FIREBASE_GUIDE &&
            currentScreen != Screen.ADMIN_PORTAL

    val showBottomBar = isLoggedIn && (
            currentScreen == Screen.DASHBOARD ||
                    currentScreen == Screen.FIND_MATCHES ||
                    currentScreen == Screen.INTERESTS ||
                    currentScreen == Screen.MESSAGES ||
                    currentScreen == Screen.SETTINGS ||
                    currentScreen == Screen.SHORTLIST ||
                    currentScreen == Screen.PROFILE_VISITORS ||
                    currentScreen == Screen.VERIFICATION ||
                    currentScreen == Screen.SAFETY_PRIVACY
            )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showTopBar) {
                BondhonAppBar(
                    title = when (currentScreen) {
                        Screen.FIND_MATCHES -> "Find Matches"
                        Screen.INTERESTS -> "Matrimonial Interests"
                        Screen.SHORTLIST -> "Shortlisted Profiles"
                        Screen.MESSAGES -> "Messages"
                        Screen.PROFILE_DETAIL -> selectedProfile?.fullName ?: "Profile Details"
                        Screen.PROFILE_WIZARD -> "Complete Profile"
                        Screen.PROFILE_VISITORS -> "Who Viewed My Profile"
                        Screen.SETTINGS -> "User Settings"
                        Screen.SAFETY_PRIVACY -> "Safety & Account"
                        Screen.VERIFICATION -> "Profile Verification"
                        Screen.SUCCESS_STORIES -> "Success Stories"
                        Screen.ABOUT_FAQ_CONTACT -> "About & Support"
                        else -> "Bondhon Matrimony"
                    },
                    canNavigateBack = canGoBack,
                    onNavigateBack = { viewModel.navigateBack() },
                    unreadNotificationCount = unreadNotifications,
                    onNotificationsClick = { viewModel.navigateTo(Screen.INTERESTS) },
                    isAdminMode = isAdmin,
                    onAdminClick = { viewModel.navigateTo(Screen.ADMIN_PORTAL) },
                    onOpenFirebaseGuide = { viewModel.navigateTo(Screen.FIREBASE_GUIDE) }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                BondhonBottomBar(
                    currentScreen = if (currentScreen == Screen.SETTINGS || currentScreen == Screen.VERIFICATION) Screen.SAFETY_PRIVACY else currentScreen,
                    onTabSelected = { target ->
                        if (target == Screen.SAFETY_PRIVACY) {
                            viewModel.navigateTo(Screen.SETTINGS)
                        } else {
                            viewModel.navigateTo(target)
                        }
                    },
                    unreadMessageCount = 1
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.LANDING -> LandingScreen(viewModel = viewModel)
                Screen.LOGIN -> LoginScreen(viewModel = viewModel)
                Screen.REGISTER -> RegisterScreen(viewModel = viewModel)
                Screen.FORGOT_PASSWORD -> ForgotPasswordScreen(viewModel = viewModel)
                Screen.DASHBOARD -> DashboardScreen(viewModel = viewModel)
                Screen.FIND_MATCHES -> FindMatchesScreen(viewModel = viewModel)
                Screen.INTERESTS -> InterestsScreen(viewModel = viewModel)
                Screen.SHORTLIST -> ShortlistScreen(viewModel = viewModel)
                Screen.MESSAGES -> ConversationListScreen(viewModel = viewModel)
                Screen.CHAT_DETAIL -> {
                    selectedConversation?.let { conv ->
                        ChatDetailScreen(conversation = conv, viewModel = viewModel)
                    } ?: DashboardScreen(viewModel = viewModel)
                }
                Screen.PROFILE_DETAIL -> {
                    selectedProfile?.let { prof ->
                        DetailedProfileScreen(profile = prof, viewModel = viewModel)
                    } ?: DashboardScreen(viewModel = viewModel)
                }
                Screen.PROFILE_WIZARD -> ProfileWizardScreen(viewModel = viewModel)
                Screen.PROFILE_VISITORS -> ProfileVisitorsScreen(viewModel = viewModel)
                Screen.SAFETY_PRIVACY -> SafetyPrivacyScreen(viewModel = viewModel)
                Screen.SETTINGS -> SettingsScreen(viewModel = viewModel)
                Screen.VERIFICATION -> VerificationScreen(viewModel = viewModel)
                Screen.SUCCESS_STORIES -> SuccessStoriesScreen(viewModel = viewModel)
                Screen.ABOUT_FAQ_CONTACT -> AboutFaqContactScreen(viewModel = viewModel)
                Screen.ADMIN_PORTAL -> AdminDashboardScreen(viewModel = viewModel)
                Screen.FIREBASE_GUIDE -> FirebaseGuideScreen(viewModel = viewModel)
            }
        }
    }
}
