package com.doston.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.todoapp.database.ChecklistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val isDarkTheme by viewModel.themeDark.collectAsState()

    // Define colors based on theme
    val backgroundColor = if (isDarkTheme) Color(0xFF1C1C1C) else Color.White
    val surfaceColor = if (isDarkTheme) Color(0xFF2C2C2C) else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val secondaryTextColor = if (isDarkTheme) Color(0xFFB0B0B0) else Color(0xFF666666)
    val accentColor = Color(0xFFFF5722) // Orange accent color
    val borderColor = if (isDarkTheme) Color(0xFF444444) else Color(0xFFE0E0E0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Settings",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = textColor
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor
            )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Theme Section
            Text(
                text = "Theme",
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Switch between light and dark themes",
                color = secondaryTextColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Theme Selection Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Dark Theme Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isDarkTheme) accentColor else surfaceColor
                        )
                        .border(
                            width = if (isDarkTheme) 0.dp else 1.dp,
                            color = if (isDarkTheme) Color.Transparent else borderColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { viewModel.setTheme(true) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Dark",
                        color = if (isDarkTheme) Color.White else textColor,
                        fontSize = 16.sp,
                        fontWeight = if (isDarkTheme) FontWeight.Medium else FontWeight.Normal
                    )
                }

                // Light Theme Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (!isDarkTheme) accentColor else surfaceColor
                        )
                        .border(
                            width = 1.dp,
                            color = if (!isDarkTheme) Color.Transparent else borderColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { viewModel.setTheme(false) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Light",
                        color = if (!isDarkTheme) Color.White else textColor,
                        fontSize = 16.sp,
                        fontWeight = if (!isDarkTheme) FontWeight.Medium else FontWeight.Normal
                    )
                }
            }

            // Preview Text Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 2.dp,
                        color = accentColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(surfaceColor)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Preview Text",
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reset to Default Button
            Button(
                onClick = {
                    viewModel.setTheme(true) // Reset to dark theme as default
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = surfaceColor,
                    contentColor = textColor
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Reset to Default",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Apply Button
            Button(
                onClick = {
                    // Apply changes and navigate back
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Apply",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}