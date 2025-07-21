package com.doston.todoapp.main

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doston.todoapp.R
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.ui.theme.ButtonBlack
import com.doston.todoapp.ui.theme.ButtonColor
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.WhiteColor

@Composable
fun HomeScreen(navController: NavController,viewModel:ChecklistViewModel) {

    val isDarkTheme by viewModel.themeDark.collectAsState()
    Scaffold(topBar = {
        Text(
            text = "Main Menu",
            color = WhiteColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }, containerColor = MainColor,
        bottomBar = {
            NavigationBar(containerColor = ButtonColor, contentColor = ButtonBlack) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("checklist") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.checklist),
                            tint = ButtonBlack,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text("Checklists", color = ButtonBlack) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("template") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.template),
                            tint = ButtonBlack,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text("Templates", color = ButtonBlack) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("archive") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.archive),
                            tint = ButtonBlack,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text("Archive", color = ButtonBlack) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("settings") },
                    icon = {
                        Icon(
                            painterResource(R.drawable.setting),
                            tint = ButtonBlack,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    label = { Text("Settings", color = ButtonBlack) }
                )

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(MainColor)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = ButtonColor
            )
            CustomButton(
                icon = R.drawable.checklist,
                text = "Checklists",
                route = "checklist",
                navController = navController
            )
            CustomButton(
                icon = R.drawable.template,
                text = "Templates",
                route = "template",
                navController = navController
            )
            CustomButton(
                icon = R.drawable.archive,
                text = "Archive",
                route = "archive",
                navController = navController
            )
            CustomButton(
                icon = R.drawable.setting,
                text = "Settings",
                route = "settings",
                navController = navController
            )
            CustomButton(
                icon = R.drawable.setting,
                text = "Help & Support",
                route = "support",
                navController = navController
            )
            CustomButton(
                icon = R.drawable.setting,
                text = "About App",
                route = "about",
                navController = navController
            )


        }
    }
}

@Composable
fun CustomButton(icon: Int, text: String, route: String, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonColor, shape = RoundedCornerShape(15.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable { navController.navigate(route) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(icon),
                modifier = Modifier.size(20.dp),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = WhiteColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
        Icon(
            painterResource(R.drawable.next),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )
    }
}






