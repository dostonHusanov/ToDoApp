package com.doston.todoapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate("onboard1")
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(painter = painterResource(R.drawable.task_white), contentDescription = null, tint = Color(0xFFFF5722), modifier = Modifier.size(80.dp))
    }
}

// == OnboardingScreen1.kt ==
@Composable
fun OnboardingScreen1(navController: NavController) {
    OnboardingTemplate(
        title = "Organize Your Tasks",
        subtitle = "Create and manage checklists with ease",
        onNext = { navController.navigate("onboard2") }
    )
}

// == OnboardingScreen2.kt ==
@Composable
fun OnboardingScreen2(navController: NavController) {
    OnboardingTemplate(
        title = "Customize Your Experience",
        subtitle = "Use templates and switch themes",
        onNext = { navController.navigate("main") }
    )
}

@Composable
fun OnboardingTemplate(title: String, subtitle: String, onNext: () -> Unit) {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(R.drawable.settings), contentDescription = null, tint = Color(0xFFFF5722), modifier = Modifier.size(80.dp))
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.LightGray)
        Spacer(Modifier.height(32.dp))
        Button (onClick = onNext) { Text("Next") }
    }
}
