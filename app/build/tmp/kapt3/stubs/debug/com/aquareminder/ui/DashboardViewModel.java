package com.aquareminder.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u0013\u001a\u00020\u0010H\u0002J\u000e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0007J\b\u0010\u0016\u001a\u00020\u0010H\u0002J\u000e\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0019R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u001a"}, d2 = {"Lcom/aquareminder/ui/DashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/aquareminder/data/WaterRepository;", "(Lcom/aquareminder/data/WaterRepository;)V", "_streakDays", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_weeklyTotals", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/aquareminder/ui/DashboardUiState;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "deleteEntry", "", "entry", "Lcom/aquareminder/data/WaterEntry;", "loadWeeklyTotals", "logWater", "amountMl", "refreshStreak", "updateSettings", "settings", "Lcom/aquareminder/data/UserSettings;", "app_debug"})
public final class DashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.aquareminder.data.WaterRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _streakDays = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<java.lang.Integer>> _weeklyTotals = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.aquareminder.ui.DashboardUiState> uiState = null;
    
    public DashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.WaterRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.aquareminder.ui.DashboardUiState> getUiState() {
        return null;
    }
    
    public final void logWater(int amountMl) {
    }
    
    public final void deleteEntry(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.WaterEntry entry) {
    }
    
    public final void updateSettings(@org.jetbrains.annotations.NotNull()
    com.aquareminder.data.UserSettings settings) {
    }
    
    private final void refreshStreak() {
    }
    
    private final void loadWeeklyTotals() {
    }
}