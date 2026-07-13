package com.aquareminder.notification;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t\u00a8\u0006\n"}, d2 = {"Lcom/aquareminder/notification/ReminderScheduler;", "", "()V", "cancelReminder", "", "context", "Landroid/content/Context;", "scheduleReminder", "intervalMinutes", "", "app_debug"})
public final class ReminderScheduler {
    @org.jetbrains.annotations.NotNull()
    public static final com.aquareminder.notification.ReminderScheduler INSTANCE = null;
    
    private ReminderScheduler() {
        super();
    }
    
    /**
     * Dipanggil saat app pertama kali dibuka, atau saat user mengubah interval
     * reminder di halaman pengaturan.
     */
    public final void scheduleReminder(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int intervalMinutes) {
    }
    
    public final void cancelReminder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}