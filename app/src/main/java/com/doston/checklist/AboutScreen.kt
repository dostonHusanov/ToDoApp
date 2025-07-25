package com.doston.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(viewModel: ChecklistViewModel) {
    val isDarkTheme by viewModel.themeDark.collectAsState()

    // Dynamic theme colors
    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)

    Scaffold(
        topBar = {
            Column {
                Text(
                    "About",
                    color = textColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = dividerColor
                )
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(accentColor)
                    .border(3.dp, textColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(100.dp),
                    tint = if (isDarkTheme) WhiteColor else Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Todo Checklist App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Version 1.0.0",
                fontSize = 16.sp,
                color = textColor.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "About This App",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "A simple and elegant todo checklist application designed to help you organize your tasks efficiently. Create checklists, mark tasks as complete, and keep your completed lists in archive for future reference.",
                        fontSize = 14.sp,
                        color = textColor,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Key Features",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    FeatureItem("✓", "Create and manage multiple checklists", textColor, accentColor)
                    FeatureItem("✓", "Mark tasks as complete or incomplete", textColor, accentColor)
                    FeatureItem("✓", "Archive completed checklists", textColor, accentColor)
                    FeatureItem("✓", "Clean and intuitive user interface", textColor, accentColor)
                    FeatureItem("✓", "Local data storage", textColor, accentColor)
                    FeatureItem("✓", "Dark and light theme support", textColor, accentColor)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Developer",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Developed by Doston",
                        fontSize = 16.sp,
                        color = textColor,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Built with Android Jetpack Compose",
                        fontSize = 14.sp,
                        color = textColor.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Current Theme",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )

                        Text(
                            text = if (isDarkTheme) "DARK" else "LIGHT",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkTheme) accentColor else Color(0xFF6200EE),
                            modifier = Modifier
                                .background(
                                    (if (isDarkTheme) accentColor else Color(0xFF6200EE)).copy(alpha = 0.1f),
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Theme changes automatically based on your system settings or can be toggled in the app settings.",
                        fontSize = 14.sp,
                        color = textColor.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "© 2025 Checklist. All rights reserved.",
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FeatureItem(
    bullet: String,
    text: String,
    textColor: Color,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bullet,
            color = accentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            lineHeight = 18.sp
        )
    }
}