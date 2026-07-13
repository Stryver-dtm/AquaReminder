package com.aquareminder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.aquareminder.data.AppDatabase
import com.aquareminder.data.WaterRepository
import com.aquareminder.notification.ReminderScheduler
import com.aquareminder.ui.AppNavigation
import com.aquareminder.ui.DashboardViewModel
import com.aquareminder.ui.Routes
import com.aquareminder.ui.theme.AquaReminderTheme

class MainActivity : ComponentActivity() {

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* granted or not, app tetap jalan */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermissionIfNeeded()

        val repository = WaterRepository(AppDatabase.getInstance(applicationContext))
        val viewModel = DashboardViewModel(repository)

        // Jadwalkan reminder periodik
        ReminderScheduler.scheduleReminder(applicationContext, intervalMinutes = 90)

        // Cek apakah onboarding sudah selesai (cek dari SharedPreferences)
        val prefs = getSharedPreferences("aqua_prefs", MODE_PRIVATE)
        val onboardingDone = prefs.getBoolean("onboarding_done", false)
        val startDestination = if (onboardingDone) Routes.DASHBOARD else Routes.ONBOARDING

        setContent {
            AquaReminderTheme(dynamicColor = false) {
                AppNavigation(
                    viewModel = viewModel,
                    startDestination = startDestination
                )
            }
        }

        // Setelah onboarding selesai, simpan flag
        if (!onboardingDone) {
            prefs.edit().putBoolean("onboarding_done", true).apply()
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
