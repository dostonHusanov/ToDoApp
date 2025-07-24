// Updated Theme.kt
package com.doston.checklist.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Fixed Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = AppColors.AccentOrange,
    onPrimary = WhiteColor,
    primaryContainer = AppColors.AccentOrangeDark,
    onPrimaryContainer = WhiteColor,
    secondary = AppColors.AccentOrangeLight,
    onSecondary = MainColor,
    tertiary = Pink80,
    background = AppColors.DarkBackground,
    onBackground = AppColors.DarkOnBackground,
    surface = AppColors.DarkSurface,
    onSurface = AppColors.DarkOnSurface,
    surfaceVariant = AppColors.DarkSurfaceVariant,
    onSurfaceVariant = AppColors.DarkOnSurfaceVariant,
    outline = AppColors.DarkOutline,
    error = AppColors.ErrorRed,
    onError = WhiteColor
)

// Fixed Light Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = AppColors.AccentOrange,
    onPrimary = WhiteColor,
    primaryContainer = AppColors.AccentOrangeLight,
    onPrimaryContainer = MainColor,
    secondary = AppColors.AccentOrangeDark,
    onSecondary = WhiteColor,
    tertiary = Pink40,
    background = AppColors.LightBackground,
    onBackground = AppColors.LightOnBackground,
    surface = AppColors.LightSurface,
    onSurface = AppColors.LightOnSurface,
    surfaceVariant = AppColors.LightSurfaceVariant,
    onSurfaceVariant = AppColors.LightOnSurfaceVariant,
    outline = AppColors.LightOutline,
    error = AppColors.ErrorRed,
    onError = WhiteColor
)

@Composable
fun ToDoAppTheme(
    darkTheme: Boolean, // Remove default parameter - always pass explicit value
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}