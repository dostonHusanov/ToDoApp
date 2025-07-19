package com.doston.todoapp.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.doston.todoapp.R
import com.doston.todoapp.ui.theme.MainColor
import com.doston.todoapp.ui.theme.WhiteColor
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate("onboarding")
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(MainColor), contentAlignment = Alignment.Center) {
        Icon(
            painter = painterResource(R.drawable.task_white),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(80.dp)
        )
    }
}
