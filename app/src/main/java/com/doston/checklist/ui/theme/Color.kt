package com.doston.checklist.ui.theme

import androidx.compose.ui.graphics.Color

// Legacy Material Design colors (keep for compatibility)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Your existing custom colors (updated and organized)
val MainColor = Color(0xFF141414)
val YellowColor = Color(0xFFE85128)  // This is actually orange
val WhiteColor = Color(0xFFFFFFFF)
val ButtonColor = Color(0xFF282828)
val ButtonBlack = Color(0xFF808080)

// Enhanced color palette for proper theming
object AppColors {
    // Dark Theme Colors
    val DarkBackground = Color(0xFF1C1C1C)
    val DarkSurface = Color(0xFF282828)
    val DarkSurfaceVariant = Color(0xFF3C3C3C)
    val DarkOnBackground = Color(0xFFFFFFFF)
    val DarkOnSurface = Color(0xFFFFFFFF)
    val DarkOnSurfaceVariant = Color(0xFFB0B0B0)
    val DarkOutline = Color(0xFF444444)

    // Light Theme Colors
    val LightBackground = Color(0xFFFFFFFF)
    val LightSurface = Color(0xFFF5F5F5)
    val LightSurfaceVariant = Color(0xFFEEEEEE)
    val LightOnBackground = Color(0xFF000000)
    val LightOnSurface = Color(0xFF000000)
    val LightOnSurfaceVariant = Color(0xFF666666)
    val LightOutline = Color(0xFFE0E0E0)

    // Accent Colors
    val AccentOrange = Color(0xFFE85128)  // Your existing YellowColor
    val AccentOrangeDark = Color(0xFFD63E1A)
    val AccentOrangeLight = Color(0xFFFF8A65)

    // Status Colors
    val SuccessGreen = Color(0xFF4CAF50)
    val WarningYellow = Color(0xFFFFEB3B)
    val ErrorRed = Color(0xFFF44336)
}