package com.aquareminder.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Routes {
    const val ONBOARDING = "onboarding"
    const val DASHBOARD  = "dashboard"
    const val HISTORY    = "history"
    const val SETTINGS   = "settings"
}

@Composable
fun AppNavigation(
    viewModel: DashboardViewModel,
    startDestination: String = Routes.ONBOARDING
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = { targetMl ->
                    viewModel.updateSettings(
                        viewModel.uiState.value.settings.copy(dailyTargetMl = targetMl)
                    )
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                },
                onSkip = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                viewModel = viewModel,
                onOpenHistory = { navController.navigate(Routes.HISTORY) },
                onOpenSettings = { navController.navigate(Routes.SETTINGS) }
            )
        }

        composable(Routes.HISTORY) {
            HistoryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.SETTINGS) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
