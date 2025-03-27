package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    primaryContainer = OrangeLight.copy(alpha = 0.7f),
    onPrimaryContainer = Color(0xFF4E2800),
    secondary = OrangeDark,
    secondaryContainer = OrangeDark.copy(alpha = 0.1f),
    onSecondaryContainer = OrangeDark,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceContainer = BlackForLightMode,
    onPrimary = OnPrimaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight,
    error = ErrorColor,
    onError = OnPrimaryLight,
    surfaceVariant = SurfaceLight.copy(alpha = 0.7f),
    onSurfaceVariant = OnSurfaceLight.copy(alpha = 0.7f),
    outline = OnSurfaceLight.copy(alpha = 0.2f)
)

private val DarkColorScheme = darkColorScheme(
    primary = OrangeLight,
    primaryContainer = OrangePrimary.copy(alpha = 0.7f),
    onPrimaryContainer = Color(0xFFFFECCE),
    secondary = OrangePrimary,
    secondaryContainer = OrangePrimary.copy(alpha = 0.1f),
    onSecondaryContainer = OrangeLight,
    background = BackgroundDark,
    surfaceContainer = BlackForDarktMode,
    surface = SurfaceDark,
    onPrimary = OnPrimaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark,
    error = ErrorColor,
    onError = OnPrimaryLight,
    surfaceVariant = SurfaceDark.copy(alpha = 0.7f),
    onSurfaceVariant = OnSurfaceDark.copy(alpha = 0.7f),
    outline = OnSurfaceDark.copy(alpha = 0.2f)
)


@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}