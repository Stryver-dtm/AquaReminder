package com.aquareminder.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import com.aquareminder.data.AppDatabase
import com.aquareminder.data.WaterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Menerima tap tombol "Sudah minum" dari notifikasi dan langsung
 * mencatat konsumsi air ke database, tanpa perlu membuka aplikasi.
 */
class QuickLogReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != NotificationHelper.ACTION_QUICK_LOG) return

        val amountMl = intent.getIntExtra(NotificationHelper.EXTRA_AMOUNT_ML, 250)
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getInstance(context)
                val repository = WaterRepository(db)
                repository.logWater(amountMl)

                // Tutup notifikasi setelah dicatat
                NotificationManagerCompat.from(context).cancel(1001)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
