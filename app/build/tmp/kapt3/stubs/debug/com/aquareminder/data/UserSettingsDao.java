package com.aquareminder.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003H\'J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/aquareminder/data/UserSettingsDao;", "", "getSettings", "Lkotlinx/coroutines/flow/Flow;", "Lcom/aquareminder/data/UserSettings;", "getSettingsSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSettings", "", "settings", "(Lcom/aquareminder/data/UserSettings;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface UserSettingsDao {
    
    @androidx.room.Query(value = "SELECT * FROM user_settings WHERE id = 1")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.aquareminder.data.UserSettings> getSettings();
    
    @androidx.room.Query(value = "SELECT * FROM user_settings WHERE id = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSettingsSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.aquareminder.data.UserSettings> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveSettings(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.UserSettings settings, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}