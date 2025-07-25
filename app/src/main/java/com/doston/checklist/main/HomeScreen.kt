package com.doston.checklist.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.checklist.R
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonBlack
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor

@Composable
fun HomeScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val isDarkTheme by viewModel.themeDark.collectAsState()
    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val dividerColor = if (isDarkTheme) ButtonColor else Color(0xFFE0E0E0)
    val navIconColor = if (isDarkTheme) ButtonBlack else Color.Black
    Scaffold(
        topBar = {
            Text(
                text = "Main Menu",
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        containerColor = backgroundColor,
        bottomBar = {
            NavigationBar(
                containerColor = cardColor,
                contentColor = navIconColor
            ) {
                val title=R.string.checklist
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("checklist") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.checklist),
                            tint = navIconColor,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },

                    label = { Text(text = stringResource(id = R.string.checklist), color = navIconColor) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = navIconColor,
                        unselectedTextColor = navIconColor,
                        indicatorColor = cardColor
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("archive") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.archive),
                            tint = navIconColor,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text("Archive", color = navIconColor) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = navIconColor,
                        unselectedTextColor = navIconColor,
                        indicatorColor = cardColor
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.setting),
                            tint = navIconColor,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text("Settings", color = navIconColor) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = navIconColor,
                        unselectedTextColor = navIconColor,
                        indicatorColor = cardColor
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(backgroundColor)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = dividerColor
            )

            CustomButton(
                icon = R.drawable.checklist,
                text = "Checklists",
                route = "checklist",
                navController = navController,
                cardColor = cardColor,
                textColor = textColor
            )

            CustomButton(
                icon = R.drawable.archive,
                text = "Archive",
                route = "archive",
                navController = navController,
                cardColor = cardColor,
                textColor = textColor
            )
            CustomButton(
                icon = R.drawable.setting,
                text = "Settings",
                route = "settings",
                navController = navController,
                cardColor = cardColor,
                textColor = textColor
            )
            CustomButton(
                icon = R.drawable.setting,
                text = "Help & Support",
                route = "support",
                navController = navController,
                cardColor = cardColor,
                textColor = textColor
            )
            CustomButton(
                icon = R.drawable.setting,
                text = "About App",
                route = "about",
                navController = navController,
                cardColor = cardColor,
                textColor = textColor
            )
        }
    }
}

@Composable
fun CustomButton(
    icon: Int,
    text: String,
    route: String,
    navController: NavController,
    cardColor: Color,
    textColor: Color
) {
    Column(modifier = Modifier.fillMaxWidth() .fillMaxWidth().clip(RoundedCornerShape(15.dp))
        .background(cardColor).clickable { navController.navigate(route) }) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(cardColor, shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(icon),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
                tint = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            painterResource(R.drawable.next),
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.size(20.dp)
        )
    }}
}