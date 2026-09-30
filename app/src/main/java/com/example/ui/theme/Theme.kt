package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = RoyalMaroonPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFEE8EC),
    onPrimaryContainer = RoyalMaroonDark,
    secondary = GoldAccent,
    onSecondary = Color(0xFF1E1400),
    secondaryContainer = Color(0xFFFFF0C4),
    onSecondaryContainer = Color(0xFF4A3700),
    tertiary = Color(0xFF8A3043),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD9DF),
    onTertiaryContainer = Color(0xFF3B0011),
    background = WarmCreamBackground,
    onBackground = TextPrimaryDark,
    surface = WarmIvorySurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = WarmSurfaceVariant,
    onSurfaceVariant = TextSecondaryMuted,
    outline = DividerColor,
    outlineVariant = ChipBorderColor,
    error = ErrorCrimson,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = RoyalMaroonLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF5A0C1D),
    onPrimaryContainer = Color(0xFFFFD9DF),
    secondary = GoldAccentLight,
    onSecondary = Color(0xFF3E2D00),
    secondaryContainer = Color(0xFF5B4300),
    onSecondaryContainer = Color(0xFFFFE088),
    tertiary = Color(0xFFFFAEB9),
    onTertiary = Color(0xFF541D2C),
    background = Color(0xFF181112),
    onBackground = Color(0xFFEDE0DF),
    surface = Color(0xFF22191B),
    onSurface = Color(0xFFEDE0DF),
    surfaceVariant = Color(0xFF332527),
    onSurfaceVariant = Color(0xFFD6C2C3),
    outline = Color(0xFF4E3D40),
    outlineVariant = Color(0xFF3B2D2F),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun BondhonMatrimonyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
