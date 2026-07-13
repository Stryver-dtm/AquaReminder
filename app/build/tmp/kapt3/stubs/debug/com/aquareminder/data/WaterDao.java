package com.aquareminder.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J$\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\'J\u0014\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\'J\u001e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0013"}, d2 = {"Lcom/aquareminder/data/WaterDao;", "", "deleteEntry", "", "entry", "Lcom/aquareminder/data/WaterEntry;", "(Lcom/aquareminder/data/WaterEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEntriesBetween", "Lkotlinx/coroutines/flow/Flow;", "", "startMillis", "", "endMillis", "getRecentEntries", "getTotalBetween", "", "getTotalBetweenSync", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertEntry", "app_debug"})
@androidx.room.Dao()
public abstract interface WaterDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEntry(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.WaterEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEntry(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.WaterEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM water_entries WHERE timestamp BETWEEN :startMillis AND :endMillis ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.aquareminder.data.WaterEntry>> getEntriesBetween(long startMillis, long endMillis);
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(amountMl), 0) FROM water_entries WHERE timestamp BETWEEN :startMillis AND :endMillis")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalBetween(long startMillis, long endMillis);
    
    @androidx.room.Query(value = "SELECT * FROM water_entries ORDER BY timestamp DESC LIMIT 500")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.aquareminder.data.WaterEntry>> getRecentEntries();
    
    @androidx.room.Query(value = "SELECT COALESCE(SUM(amountMl), 0) FROM water_entries WHERE timestamp BETWEEN :startMillis AND :endMillis")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getTotalBetweenSync(long startMillis, long endMillis, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}