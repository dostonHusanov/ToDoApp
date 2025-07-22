package com.doston.todoapp.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doston.todoapp.AboutScreen
import com.doston.todoapp.ActiveChecklistScreen
import com.doston.todoapp.archive.ArchiveScreen
import com.doston.todoapp.CreateChecklistScreen
import com.doston.todoapp.SettingsScreen
import com.doston.todoapp.SupportScreen
import com.doston.todoapp.TemplateScreen
import com.doston.todoapp.database.ChecklistViewModel
import com.doston.todoapp.main.HomeScreen
import com.doston.todoapp.onboarding.LoadingScreen
import com.doston.todoapp.onboarding.OnBoardingScreen

@SuppressLint("RememberReturnType")
@Composable
fun MainNav(context: Context,viewModel: ChecklistViewModel) {
    val navController = rememberNavController()

    val hasCompletedOnboarding = remember { mutableStateOf(hasCompletedOnboarding(context)) }

    NavHost(
        navController = navController,
        startDestination = if (hasCompletedOnboarding.value) "main" else "loading"
    ) {
        composable("loading") {
           LoadingScreen(navController,viewModel)
        }



        composable("onboarding") {
            OnBoardingScreen(onFinish = {

                hasCompletedOnboarding.value = true
                setOnboardingCompleted(context, true)
                navController.navigate("main")
            },viewModel)
        }

        composable("main") {
            HomeScreen(navController = navController,viewModel)
        }


        composable("checklist") { ActiveChecklistScreen(navController,viewModel) }
        composable("template") { TemplateScreen(navController,viewModel) }

        composable("settings") { SettingsScreen(navController,viewModel) }
        composable("create") { CreateChecklistScreen(navController,viewModel) }
        composable("archive") { ArchiveScreen(navController,viewModel) }
        composable("about") { AboutScreen(viewModel) }
        composable("support") { SupportScreen(viewModel)  }

    }
}



fun hasCompletedOnboarding(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("hasCompletedOnboarding", false)
}

fun setOnboardingCompleted(context: Context, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("hasCompletedOnboarding", value).apply()
}