package com.aquareminder.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aquareminder.notification.ReminderScheduler
import com.aquareminder.ui.theme.AquaGradientEnd
import com.aquareminder.ui.theme.AquaGradientStart
import com.aquareminder.ui.theme.IceWhite
import com.aquareminder.ui.theme.MistBlue
import com.aquareminder.data.UserSettings

private object AquaRoutes {
    const val Launch = "launch"
    const val Onboarding = "onboarding"
    const val Dashboard = "dashboard"
    const val History = "history"
    const val Settings = "settings"
}

@Composable
fun AquaReminderApp(viewModel: DashboardViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(
        state.isLoading,
        state.hasSettings,
        state.settings.reminderIntervalMinutes,
        state.settings.isReminderEnabled
    ) {
        if (!state.isLoading && state.hasSettings) {
            if (state.settings.isReminderEnabled) {
                ReminderScheduler.scheduleReminder(
                    context = context.applicationContext,
                    intervalMinutes = state.settings.reminderIntervalMinutes
                )
            } else {
                ReminderScheduler.cancelReminder(context.applicationContext)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AquaRoutes.Launch
    ) {
        composable(AquaRoutes.Launch) {
            LaunchScreen()
            LaunchedEffect(state.isLoading, state.hasSettings) {
                if (!state.isLoading) {
                    navController.navigate(
                        if (state.hasSettings) AquaRoutes.Dashboard else AquaRoutes.Onboarding
                    ) {
                        popUpTo(AquaRoutes.Launch) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
        composable(AquaRoutes.Onboarding) {
            OnboardingScreen(
                onFinish = { target ->
                    viewModel.updateSettings(UserSettings(dailyTargetMl = target))
                    navController.navigate(AquaRoutes.Dashboard) {
                        popUpTo(AquaRoutes.Onboarding) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onSkip = {
                    viewModel.updateSettings(UserSettings())
                    navController.navigate(AquaRoutes.Dashboard) {
                        popUpTo(AquaRoutes.Onboarding) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(AquaRoutes.Dashboard) {
            DashboardScreen(
                viewModel = viewModel,
                onOpenHistory = { navController.navigate(AquaRoutes.History) },
                onOpenSettings = { navController.navigate(AquaRoutes.Settings) }
            )
        }
        composable(AquaRoutes.History) {
            HistoryScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable(AquaRoutes.Settings) {
            SettingsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun LaunchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(IceWhite, MistBlue, AquaGradientStart.copy(alpha = 0.22f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                modifier = Modifier.size(42.dp),
                color = AquaGradientEnd,
                trackColor = AquaGradientStart.copy(alpha = 0.16f)
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = "AquaReminder",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Menyiapkan hidrasi harian",
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.68f)
            )
        }
    }
}
