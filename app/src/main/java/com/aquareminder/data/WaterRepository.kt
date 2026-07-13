package com.aquareminder.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class WaterRepository(private val db: AppDatabase) {

    private val waterDao = db.waterDao()
    private val settingsDao = db.userSettingsDao()

    suspend fun logWater(amountMl: Int) {
        waterDao.insertEntry(WaterEntry(amountMl = amountMl))
    }

    suspend fun deleteEntry(entry: WaterEntry) = waterDao.deleteEntry(entry)

    fun getTodayTotal(): Flow<Int> {
        val (start, end) = todayRangeMillis()
        return waterDao.getTotalBetween(start, end)
    }

    fun getTodayEntries(): Flow<List<WaterEntry>> {
        val (start, end) = todayRangeMillis()
        return waterDao.getEntriesBetween(start, end)
    }

    fun getRecentEntries(): Flow<List<WaterEntry>> = waterDao.getRecentEntries()

    fun getSettings(): Flow<UserSettings?> = settingsDao.getSettings()

    suspend fun saveSettings(settings: UserSettings) = settingsDao.saveSettings(settings)

    /** Hitung streak: berapa hari berturut-turut target harian tercapai (mundur dari kemarin). */
    suspend fun calculateStreak(): Int {
        val settings = settingsDao.getSettingsSync() ?: UserSettings()
        val target = settings.dailyTargetMl
        var streak = 0
        val cal = Calendar.getInstance()
        // Mundur dari kemarin
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.DAY_OF_MONTH, -1)

        for (i in 0 until 365) {
            val start = cal.timeInMillis
            cal.add(Calendar.DAY_OF_MONTH, 1)
            val end = cal.timeInMillis - 1
            cal.add(Calendar.DAY_OF_MONTH, -2) // kembali ke hari sebelumnya
            val total = waterDao.getTotalBetweenSync(start, end)
            if (total >= target) streak++ else break
        }
        return streak
    }

    /** Kembalikan total air per hari untuk N hari terakhir (termasuk hari ini). */
    fun getWeeklyTotals(): Flow<List<WaterEntry>> = waterDao.getRecentEntries()

    fun getDayTotal(offsetDaysFromToday: Int): Flow<Int> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.DAY_OF_MONTH, -offsetDaysFromToday)
        val start = cal.timeInMillis
        cal.add(Calendar.DAY_OF_MONTH, 1)
        val end = cal.timeInMillis - 1
        return waterDao.getTotalBetween(start, end)
    }

    fun getDayEntries(offsetDaysFromToday: Int): Flow<List<WaterEntry>> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.DAY_OF_MONTH, -offsetDaysFromToday)
        val start = cal.timeInMillis
        cal.add(Calendar.DAY_OF_MONTH, 1)
        val end = cal.timeInMillis - 1
        return waterDao.getEntriesBetween(start, end)
    }

    private fun todayRangeMillis(): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis
        cal.add(Calendar.DAY_OF_MONTH, 1)
        val end = cal.timeInMillis - 1
        return start to end
    }
}
