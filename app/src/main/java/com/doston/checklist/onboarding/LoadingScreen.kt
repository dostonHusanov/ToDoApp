package com.doston.checklist.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.doston.checklist.R
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.ui.theme.ButtonColor
import com.doston.checklist.ui.theme.MainColor
import com.doston.checklist.ui.theme.WhiteColor
import com.doston.checklist.ui.theme.YellowColor
import kotlinx.coroutines.delay
@Composable
fun LoadingScreen(navController: NavController, viewModel: ChecklistViewModel) {
    val isDarkTheme by viewModel.themeDark.collectAsState()

    val checklists by viewModel.archivedChecklists.collectAsState()


    val backgroundColor = if (isDarkTheme) MainColor else Color(0xFFF5F5F5)
    val textColor = if (isDarkTheme) WhiteColor else Color.Black
    val cardColor = if (isDarkTheme) ButtonColor else Color.White
    val accentColor = if (isDarkTheme) YellowColor else Color(0xFF6200EE)
    val topBarColor = if (isDarkTheme) ButtonColor else Color.White
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate("onboarding")
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                if (isDarkTheme) R.drawable.task_white
                else R.drawable.task_white // You might want a dark version for light theme
            ),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(80.dp)
        )
    }
}