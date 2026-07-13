package com.aquareminder.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert
    suspend fun insertEntry(entry: WaterEntry)

    @Delete
    suspend fun deleteEntry(entry: WaterEntry)

    @Query("SELECT * FROM water_entries WHERE timestamp BETWEEN :startMillis AND :endMillis ORDER BY timestamp DESC")
    fun getEntriesBetween(startMillis: Long, endMillis: Long): Flow<List<WaterEntry>>

    @Query("SELECT COALESCE(SUM(amountMl), 0) FROM water_entries WHERE timestamp BETWEEN :startMillis AND :endMillis")
    fun getTotalBetween(startMillis: Long, endMillis: Long): Flow<Int>

    @Query("SELECT * FROM water_entries ORDER BY timestamp DESC LIMIT 500")
    fun getRecentEntries(): Flow<List<WaterEntry>>

    @Query("SELECT COALESCE(SUM(amountMl), 0) FROM water_entries WHERE timestamp BETWEEN :startMillis AND :endMillis")
    suspend fun getTotalBetweenSync(startMillis: Long, endMillis: Long): Int
}

@Dao
interface UserSettingsDao {

    @Query("SELECT * FROM user_settings WHERE id = 1")
    fun getSettings(): Flow<UserSettings?>

    @Query("SELECT * FROM user_settings WHERE id = 1")
    suspend fun getSettingsSync(): UserSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: UserSettings)
}
