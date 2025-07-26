package com.doston.checklist.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doston.checklist.AboutScreen
import com.doston.checklist.LocaleManager
import com.doston.checklist.SettingsScreen
import com.doston.checklist.SupportScreen
import com.doston.checklist.archive.ArchiveInfoScreen
import com.doston.checklist.archive.ArchiveScreen
import com.doston.checklist.checklist.ActiveChecklistScreen
import com.doston.checklist.checklist.CheckListInfoScreen
import com.doston.checklist.checklist.CreateChecklistScreen
import com.doston.checklist.database.ChecklistViewModel
import com.doston.checklist.main.HomeScreen
import com.doston.checklist.onboarding.LanguageScreen
import com.doston.checklist.onboarding.LoadingScreen
import com.doston.checklist.onboarding.OnBoardingScreen

@SuppressLint("RememberReturnType")
@Composable
fun MainNav(context: Context, viewModel: ChecklistViewModel) {
    val navController = rememberNavController()

    val hasCompletedOnboarding = remember { mutableStateOf(hasCompletedOnboarding(context)) }

    NavHost(
        navController = navController,
        startDestination = if (hasCompletedOnboarding.value) "main" else "loading"
    ) {
        composable("loading") {
            LoadingScreen(navController, viewModel)
        }
        composable("language") {
            LanguageScreen(
                onLanguageSelected = { languageCode ->
                    val activity = context as? Activity

                    LocaleManager.setLocale(context, languageCode)
                    LocaleManager.getPrefs(context).edit().putBoolean("language_selected", true)
                        .apply()

                    activity?.recreate()
                },
                onNextClicked = {
                    navController.navigate("onboarding") {
                        popUpTo("language") { inclusive = true }
                    }
                }
            )
        }



        composable("onboarding") {
            OnBoardingScreen(onFinish = {

                hasCompletedOnboarding.value = true
                setOnboardingCompleted(context, true)
                navController.navigate("main")
            }, viewModel)
        }

        composable("main") {
            HomeScreen(navController = navController, viewModel)
        }


        composable("checklist") { ActiveChecklistScreen(navController, viewModel) }


        composable("settings") { SettingsScreen(navController, viewModel) }
        composable("create") { CreateChecklistScreen(navController, viewModel) }
        composable("archive") { ArchiveScreen(navController, viewModel) }
        composable("about") { AboutScreen(viewModel) }
        composable("support") { SupportScreen(viewModel) }
        composable("archive_info") {
            ArchiveInfoScreen(viewModel = viewModel, navController = navController)
        }
        composable("info") {
            CheckListInfoScreen(navController = navController, viewModel = viewModel)
        }



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