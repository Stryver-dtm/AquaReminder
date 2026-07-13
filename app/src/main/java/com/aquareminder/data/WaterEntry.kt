package com.aquareminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Satu catatan konsumsi air putih.
 */
@Entity(tableName = "water_entries")
data class WaterEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amountMl: Int,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Pengaturan pengguna (target harian, interval reminder, jam aktif, dll).
 * Disimpan sebagai satu baris tunggal (id selalu 1).
 */
@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    val dailyTargetMl: Int = 2000,
    val glassSizeMl: Int = 250,
    val reminderIntervalMinutes: Int = 90,
    val activeStartHour: Int = 7,
    val activeEndHour: Int = 22,
    val isReminderEnabled: Boolean = true
)
