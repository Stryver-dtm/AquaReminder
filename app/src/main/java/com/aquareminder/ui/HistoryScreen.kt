package com.aquareminder.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aquareminder.data.WaterEntry
import java.text.SimpleDateFormat
import java.util.*

private val HBgDark        = Color(0xFF0A0E1A)
private val HCardDark      = Color(0xFF141824)
private val HCardDark2     = Color(0xFF1C2235)
private val HAquaCyan      = Color(0xFF00D4FF)
private val HAquaBlue      = Color(0xFF0066FF)
private val HNeonGreen     = Color(0xFF39FF6B)
private val HTrackGray     = Color(0xFF252A3A)
private val HTextPrimary   = Color(0xFFFFFFFF)
private val HTextSecondary = Color(0xFF8B9DC3)
private val HOrangeAccent  = Color(0xFFFF9F43)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: DashboardViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = HBgDark,
        topBar = {
            TopAppBar(
                title = { Text("Riwayat & Statistik", color = HTextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = HAquaCyan)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = HBgDark)
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp, end = 20.dp,
                top = padding.calculateTopPadding() + 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { WeeklySummaryCard(weeklyTotals = state.weeklyTotals, targetMl = state.targetMl) }
            item { HistoryStatsRow(state = state) }
            item {
                Text("Catatan Lengkap", color = HTextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp, modifier = Modifier.padding(top = 8.dp))
            }

            if (state.recentEntries.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("💧", fontSize = 48.sp)
                            Spacer(Modifier.height(12.dp))
                            Text("Belum ada catatan", color = HTextSecondary, fontSize = 16.sp)
                        }
                    }
                }
            } else {
                val grouped = state.recentEntries.groupBy {
                    SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id")).format(Date(it.timestamp))
                }
                grouped.entries.forEach { (dateLabel, entries) ->
                    item {
                        Text(dateLabel, color = HAquaCyan, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                    }
                    items(entries) { entry -> HistoryEntryRow(entry = entry) }
                    item {
                        val dayTotal = entries.sumOf { it.amountMl }
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.End) {
                            Text("Total: $dayTotal ml", color = if (dayTotal >= state.targetMl) HNeonGreen else HTextSecondary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        HorizontalDivider(color = HTrackGray, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklySummaryCard(weeklyTotals: List<Int>, targetMl: Int) {
    val dayLabels = listOf("Sen","Sel","Rab","Kam","Jum","Sab","Min")
    val cal = Calendar.getInstance()
    val todayDow = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7

    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(HCardDark).padding(20.dp)
    ) {
        Text("7 Hari Terakhir", color = HTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(120.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            val maxVal = weeklyTotals.maxOrNull()?.coerceAtLeast(targetMl) ?: targetMl
            weeklyTotals.take(7).forEachIndexed { index, total ->
                val fraction = if (maxVal == 0) 0f else total.toFloat() / maxVal
                val isToday = index == 0
                val reached = total >= targetMl
                val animFraction by animateFloatAsState(fraction, tween(900, index * 100), label = "hist$index")
                val dayOffset = (todayDow - index + 7) % 7
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    if (total > 0) Text("${total}", color = if (isToday) HAquaCyan else if (reached) HNeonGreen else HTextSecondary, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height((animFraction * 90).dp.coerceAtLeast(4.dp))
                            .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(
                                when {
                                    isToday -> Brush.verticalGradient(listOf(HAquaCyan, HAquaBlue))
                                    reached -> Brush.verticalGradient(listOf(HNeonGreen, HNeonGreen.copy(alpha = 0.6f)))
                                    else    -> Brush.verticalGradient(listOf(HTrackGray, HTrackGray.copy(alpha = 0.6f)))
                                }
                            )
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(dayLabels[dayOffset], color = if (isToday) HAquaCyan else HTextSecondary, fontSize = 10.sp, fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal)
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(HAquaCyan))
            Text("Hari ini", color = HTextSecondary, fontSize = 11.sp)
            Spacer(Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(HNeonGreen))
            Text("Target tercapai", color = HTextSecondary, fontSize = 11.sp)
        }
    }
}

@Composable
private fun HistoryStatsRow(state: DashboardUiState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Column(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(HCardDark).padding(16.dp)) {
            Text("🔥", fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text("${state.streakDays}", color = HOrangeAccent, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Text("Hari Streak", color = HTextSecondary, fontSize = 12.sp)
        }
        Column(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(HCardDark).padding(16.dp)) {
            Text("📊", fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text("${state.weeklyTotals.sum()}", color = HAquaCyan, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Text("ml minggu ini", color = HTextSecondary, fontSize = 12.sp)
        }
        Column(modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp)).background(HCardDark).padding(16.dp)) {
            Text("✅", fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text("${state.weeklyTotals.count { it >= state.targetMl }}/7", color = HNeonGreen, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold)
            Text("Hari tercapai", color = HTextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
private fun HistoryEntryRow(entry: WaterEntry) {
    val sdf = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(HCardDark2).padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(HAquaBlue.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                Text("💧", fontSize = 16.sp)
            }
            Text("+${entry.amountMl} ml", color = HTextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
        Text(sdf.format(Date(entry.timestamp)), color = HTextSecondary, fontSize = 13.sp)
    }
}
