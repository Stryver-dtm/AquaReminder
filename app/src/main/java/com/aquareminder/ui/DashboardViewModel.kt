package com.aquareminder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquareminder.data.UserSettings
import com.aquareminder.data.WaterEntry
import com.aquareminder.data.WaterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DashboardUiState(
    val todayTotalMl: Int = 0,
    val targetMl: Int = 2000,
    val glassSizeMl: Int = 250,
    val streakDays: Int = 0,
    val recentEntries: List<WaterEntry> = emptyList(),
    val settings: UserSettings = UserSettings(),
    /** Total air 7 hari terakhir (index 0 = hari ini, 6 = 6 hari lalu) */
    val weeklyTotals: List<Int> = List(7) { 0 },
    val isLoading: Boolean = true,
    val hasSettings: Boolean = false
) {
    val progressFraction: Float
        get() = if (targetMl == 0) 0f else (todayTotalMl.toFloat() / targetMl).coerceIn(0f, 1f)

    val remainingMl: Int
        get() = (targetMl - todayTotalMl).coerceAtLeast(0)

    val cupsConsumed: Int
        get() = if (glassSizeMl == 0) 0 else todayTotalMl / glassSizeMl

    val totalCupsTarget: Int
        get() = if (glassSizeMl == 0) 0 else targetMl / glassSizeMl
}

class DashboardViewModel(private val repository: WaterRepository) : ViewModel() {

    private val _streakDays = MutableStateFlow(0)
    private val _weeklyTotals = MutableStateFlow(List(7) { 0 })

    val uiState: StateFlow<DashboardUiState> = combine(
        repository.getTodayTotal(),
        repository.getSettings(),
        repository.getTodayEntries(),
        _streakDays,
        _weeklyTotals
    ) { total, settings, entries, streak, weekly ->
        DashboardUiState(
            todayTotalMl = total,
            targetMl = settings?.dailyTargetMl ?: 2000,
            glassSizeMl = settings?.glassSizeMl ?: 250,
            streakDays = streak,
            recentEntries = entries,
            settings = settings ?: UserSettings(),
            weeklyTotals = weekly,
            isLoading = false,
            hasSettings = settings != null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardUiState()
    )

    init {
        refreshStreak()
        loadWeeklyTotals()
    }

    fun logWater(amountMl: Int) {
        viewModelScope.launch {
            repository.logWater(amountMl)
            refreshStreak()
        }
    }

    fun deleteEntry(entry: WaterEntry) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }

    fun updateSettings(settings: UserSettings) {
        viewModelScope.launch {
            repository.saveSettings(settings)
        }
    }

    private fun refreshStreak() {
        viewModelScope.launch {
            _streakDays.value = repository.calculateStreak()
        }
    }

    private fun loadWeeklyTotals() {
        viewModelScope.launch {
            val totals = mutableListOf<Int>()
            // index 0 = hari ini, 6 = 6 hari lalu
            for (i in 0 until 7) {
                repository.getDayTotal(i).first().let { totals.add(it) }
            }
            _weeklyTotals.value = totals
        }
    }
}
