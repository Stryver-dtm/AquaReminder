package com.aquareminder.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u00122\u0006\u0010\u0014\u001a\u00020\nJ\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u00122\u0006\u0010\u0014\u001a\u00020\nJ\u0012\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u0012J\u000e\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0012J\u0012\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u0012J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u0012J\u0012\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00130\u0012J\u0016\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010!J\u0014\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020$0#H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/aquareminder/data/WaterRepository;", "", "db", "Lcom/aquareminder/data/AppDatabase;", "(Lcom/aquareminder/data/AppDatabase;)V", "settingsDao", "Lcom/aquareminder/data/UserSettingsDao;", "waterDao", "Lcom/aquareminder/data/WaterDao;", "calculateStreak", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEntry", "", "entry", "Lcom/aquareminder/data/WaterEntry;", "(Lcom/aquareminder/data/WaterEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDayEntries", "Lkotlinx/coroutines/flow/Flow;", "", "offsetDaysFromToday", "getDayTotal", "getRecentEntries", "getSettings", "Lcom/aquareminder/data/UserSettings;", "getTodayEntries", "getTodayTotal", "getWeeklyTotals", "logWater", "amountMl", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSettings", "settings", "(Lcom/aquareminder/data/UserSettings;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "todayRangeMillis", "Lkotlin/Pair;", "", "app_debug"})
public final class WaterRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.aquareminder.data.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.aquareminder.data.WaterDao waterDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.aquareminder.data.UserSettingsDao settingsDao = null;
    
    public WaterRepository(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.AppDatabase db) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logWater(int amountMl, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteEntry(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.WaterEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTodayTotal() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.aquareminder.data.WaterEntry>> getTodayEntries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.aquareminder.data.WaterEntry>> getRecentEntries() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.aquareminder.data.UserSettings> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveSettings(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.UserSettings settings, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Hitung streak: berapa hari berturut-turut target harian tercapai (mundur dari kemarin).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object calculateStreak(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Kembalikan total air per hari untuk N hari terakhir (termasuk hari ini).
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.aquareminder.data.WaterEntry>> getWeeklyTotals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getDayTotal(int offsetDaysFromToday) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.aquareminder.data.WaterEntry>> getDayEntries(int offsetDaysFromToday) {
        return null;
    }
    
    private final kotlin.Pair<java.lang.Long, java.lang.Long> todayRangeMillis() {
        return null;
    }
}