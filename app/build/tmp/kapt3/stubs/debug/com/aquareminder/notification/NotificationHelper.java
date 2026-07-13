package com.aquareminder.notification;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/aquareminder/notification/NotificationHelper;", "", "()V", "ACTION_QUICK_LOG", "", "CHANNEL_ID", "EXTRA_AMOUNT_ML", "NOTIFICATION_ID", "", "createChannel", "", "context", "Landroid/content/Context;", "showReminderNotification", "glassSizeMl", "app_debug"})
public final class NotificationHelper {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "water_reminder_channel";
    private static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_QUICK_LOG = "com.aquareminder.ACTION_QUICK_LOG";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_AMOUNT_ML = "extra_amount_ml";
    @org.jetbrains.annotations.NotNull()
    public static final com.aquareminder.notification.NotificationHelper INSTANCE = null;
    
    private NotificationHelper() {
        super();
    }
    
    public final void createChannel(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void showReminderNotification(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int glassSizeMl) {
    }
}