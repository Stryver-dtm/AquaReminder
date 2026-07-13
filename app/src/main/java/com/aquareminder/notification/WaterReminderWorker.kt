package com.aquareminder.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aquareminder.data.AppDatabase
import kotlinx.coroutines.flow.first
import java.util.Calendar

/**
 * Worker yang dijalankan berkala (misal tiap 90 menit) oleh WorkManager.
 * Tugasnya: cek apakah sekarang jam aktif reminder, lalu tampilkan notifikasi.
 */
class WaterReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val settings = db.userSettingsDao().getSettings().first()
            ?: return Result.success() // belum ada pengaturan, skip

        if (!settings.isReminderEnabled) return Result.success()

        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val isWithinActiveHours = currentHour in settings.activeStartHour until settings.activeEndHour

        if (isWithinActiveHours) {
            NotificationHelper.showReminderNotification(
                context = applicationContext,
                glassSizeMl = settings.glassSizeMl
            )
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "water_reminder_periodic_work"
    }
}
