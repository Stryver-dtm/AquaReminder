package com.aquareminder.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {

    private const val CHANNEL_ID = "water_reminder_channel"
    private const val NOTIFICATION_ID = 1001

    // Action string yang ditangkap oleh QuickLogReceiver saat user tap tombol di notifikasi
    const val ACTION_QUICK_LOG = "com.aquareminder.ACTION_QUICK_LOG"
    const val EXTRA_AMOUNT_ML = "extra_amount_ml"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Pengingat Minum Air",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifikasi berkala untuk mengingatkan minum air putih"
            }
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun showReminderNotification(context: Context, glassSizeMl: Int) {
        createChannel(context)

        // Intent untuk action button "Sudah minum" -> langsung log tanpa buka app
        val quickLogIntent = Intent(context, QuickLogReceiver::class.java).apply {
            action = ACTION_QUICK_LOG
            putExtra(EXTRA_AMOUNT_ML, glassSizeMl)
        }
        val quickLogPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            quickLogIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // ganti dengan ikon app asli
            .setContentTitle("Waktunya minum air")
            .setContentText("Jaga hidrasi tubuhmu, yuk minum segelas air sekarang.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(
                android.R.drawable.ic_input_add,
                "Sudah minum (${glassSizeMl}ml)",
                quickLogPendingIntent
            )
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }
}
