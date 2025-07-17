package com.doston.todoapp
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ChecklistNavHost(viewModel: ChecklistViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "loading") {
        composable("loading") { LoadingScreen(navController) }
        composable("onboard1") { OnboardingScreen1(navController) }
        composable("onboard2") { OnboardingScreen2(navController) }
        composable("main") { MainMenuScreen(navController) }
        composable("active") { ActiveChecklistScreen(navController, viewModel) }
        composable("archive") { ArchiveScreen(navController, viewModel) }
        composable("create") { CreateChecklistScreen(navController, viewModel) }
        composable("templates") { TemplateScreen(navController, viewModel) }
        composable("settings") { SettingsScreen(navController, viewModel) }
    }
}