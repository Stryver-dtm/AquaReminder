package com.aquareminder.notification

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    /**
     * Dipanggil saat app pertama kali dibuka, atau saat user mengubah interval
     * reminder di halaman pengaturan.
     */
    fun scheduleReminder(context: Context, intervalMinutes: Int) {
        // WorkManager periodic minimal interval adalah 15 menit
        val safeInterval = intervalMinutes.coerceAtLeast(15)

        val request = PeriodicWorkRequestBuilder<WaterReminderWorker>(
            safeInterval.toLong(), TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiresBatteryNotLow(false)
                .build()
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WaterReminderWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE, // update kalau interval berubah
            request
        )
    }

    fun cancelReminder(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WaterReminderWorker.WORK_NAME)
    }
}
