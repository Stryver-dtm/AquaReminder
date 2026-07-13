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
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.*

// ─── Color palette (Apple Fitness inspired, aqua/water theme) ───────────────
private val BgDark       = Color(0xFF0A0E1A)
private val CardDark     = Color(0xFF141824)
private val CardDark2    = Color(0xFF1C2235)
private val AquaCyan     = Color(0xFF00D4FF)
private val AquaBlue     = Color(0xFF0066FF)
private val NeonGreen    = Color(0xFF39FF6B)
private val PurpleAccent = Color(0xFFBB86FC)
private val OrangeAccent = Color(0xFFFF9F43)
private val TrackGray    = Color(0xFF252A3A)
private val TextPrimary  = Color(0xFFFFFFFF)
private val TextSecondary= Color(0xFF8B9DC3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onOpenHistory: () -> Unit,
    onOpenSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showCustomDialog by remember { mutableStateOf(false) }
    var customAmountText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // ── Header ──────────────────────────────────────────────────────
            item {
                DashboardHeader(
                    streakDays = state.streakDays,
                    onOpenSettings = onOpenSettings
                )
            }

            // ── Animated Ring + Stats ────────────────────────────────────────
            item {
                ActivityRingSection(state = state)
            }

            // ── Quick Log Buttons ─────────────────────────────────────────────
            item {
                QuickLogSection(
                    glassSizeMl = state.glassSizeMl,
                    onLog = { viewModel.logWater(it) },
                    onCustom = { showCustomDialog = true }
                )
            }

            // ── Hydration Stats Cards ─────────────────────────────────────────
            item {
                HydrationStatsRow(state = state)
            }

            // ── Weekly Bar Chart ──────────────────────────────────────────────
            item {
                WeeklyChartCard(
                    weeklyTotals = state.weeklyTotals,
                    targetMl = state.targetMl,
                    onOpenHistory = onOpenHistory
                )
            }

            // ── Today's Log List ──────────────────────────────────────────────
            item {
                TodayLogHeader(count = state.recentEntries.size)
            }

            if (state.recentEntries.isEmpty()) {
                item { EmptyLogCard() }
            } else {
                items(state.recentEntries.take(5)) { entry ->
                    LogEntryRow(
                        entry = entry,
                        onDelete = { viewModel.deleteEntry(entry) }
                    )
                }
                if (state.recentEntries.size > 5) {
                    item {
                        TextButton(
                            onClick = onOpenHistory,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text(
                                "Lihat semua ${state.recentEntries.size} catatan",
                                color = AquaCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }

    // Custom amount dialog
    if (showCustomDialog) {
        AlertDialog(
            onDismissRequest = { showCustomDialog = false },
            containerColor = CardDark2,
            title = { Text("Jumlah kustom", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = customAmountText,
                    onValueChange = { customAmountText = it.filter { c -> c.isDigit() } },
                    label = { Text("Jumlah (ml)", color = TextSecondary) },
                    suffix = { Text("ml", color = TextSecondary) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = AquaCyan,
                        unfocusedBorderColor = TrackGray,
                        cursorColor = AquaCyan
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amt = customAmountText.toIntOrNull() ?: 0
                        if (amt > 0) viewModel.logWater(amt)
                        showCustomDialog = false
                        customAmountText = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AquaBlue)
                ) { Text("Catat", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showCustomDialog = false; customAmountText = "" }) {
                    Text("Batal", color = TextSecondary)
                }
            }
        )
    }
}

// ─── Header ──────────────────────────────────────────────────────────────────
@Composable
private fun DashboardHeader(streakDays: Int, onOpenSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            val sdf = SimpleDateFormat("EEEE, d MMMM", Locale("id"))
            Text(
                text = sdf.format(Date()).uppercase(),
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Hidrasi Harian",
                color = TextPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Streak badge
            if (streakDays > 0) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(listOf(OrangeAccent.copy(alpha = 0.2f), OrangeAccent.copy(alpha = 0.1f)))
                        )
                        .border(1.dp, OrangeAccent.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("🔥", fontSize = 14.sp)
                        Text(
                            text = "${streakDays}d",
                            color = OrangeAccent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            IconButton(
                onClick = onOpenSettings,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(CardDark)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Pengaturan", tint = TextSecondary, modifier = Modifier.size(20.dp))
            }
        }
    }
}

// ─── Animated Activity Ring Section ──────────────────────────────────────────
@Composable
private fun ActivityRingSection(state: DashboardUiState) {
    val animatedProgress by animateFloatAsState(
        targetValue = state.progressFraction,
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(CardDark)
            .padding(24.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Multi-ring canvas
            Box(
                modifier = Modifier.size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    // Outer ring (Hydration - Aqua)
                    val outerRadius = size.minDimension / 2 - 12f
                    val strokeOuter = 22f
                    drawArc(
                        color = TrackGray,
                        startAngle = -90f, sweepAngle = 360f, useCenter = false,
                        topLeft = Offset(center.x - outerRadius, center.y - outerRadius),
                        size = Size(outerRadius * 2, outerRadius * 2),
                        style = Stroke(strokeOuter, cap = StrokeCap.Round)
                    )
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(AquaCyan, AquaBlue, AquaCyan),
                            center = center
                        ),
                        startAngle = -90f,
                        sweepAngle = 360f * animatedProgress,
                        useCenter = false,
                        topLeft = Offset(center.x - outerRadius, center.y - outerRadius),
                        size = Size(outerRadius * 2, outerRadius * 2),
                        style = Stroke(strokeOuter, cap = StrokeCap.Round)
                    )

                    // Middle ring (Cups - Green)
                    val midRadius = outerRadius - strokeOuter - 6f
                    val cupProgress = (state.cupsConsumed.toFloat() / state.totalCupsTarget.coerceAtLeast(1)).coerceIn(0f, 1f)
                    val animCup = cupProgress // not separately animated to keep it simple
                    drawArc(
                        color = TrackGray,
                        startAngle = -90f, sweepAngle = 360f, useCenter = false,
                        topLeft = Offset(center.x - midRadius, center.y - midRadius),
                        size = Size(midRadius * 2, midRadius * 2),
                        style = Stroke(18f, cap = StrokeCap.Round)
                    )
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(NeonGreen, Color(0xFF00FF88), NeonGreen),
                            center = center
                        ),
                        startAngle = -90f,
                        sweepAngle = 360f * animCup,
                        useCenter = false,
                        topLeft = Offset(center.x - midRadius, center.y - midRadius),
                        size = Size(midRadius * 2, midRadius * 2),
                        style = Stroke(18f, cap = StrokeCap.Round)
                    )

                    // Inner ring (Streak - Purple)
                    val innerRadius = midRadius - 18f - 6f
                    val streakProgress = (state.streakDays.toFloat() / 30f).coerceIn(0f, 1f)
                    drawArc(
                        color = TrackGray,
                        startAngle = -90f, sweepAngle = 360f, useCenter = false,
                        topLeft = Offset(center.x - innerRadius, center.y - innerRadius),
                        size = Size(innerRadius * 2, innerRadius * 2),
                        style = Stroke(14f, cap = StrokeCap.Round)
                    )
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(PurpleAccent, Color(0xFFFF79C6), PurpleAccent),
                            center = center
                        ),
                        startAngle = -90f,
                        sweepAngle = 360f * streakProgress,
                        useCenter = false,
                        topLeft = Offset(center.x - innerRadius, center.y - innerRadius),
                        size = Size(innerRadius * 2, innerRadius * 2),
                        style = Stroke(14f, cap = StrokeCap.Round)
                    )
                }
                // Center text
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(state.progressFraction * 100).toInt()}%",
                        color = TextPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "tercapai",
                        color = TextSecondary,
                        fontSize = 10.sp
                    )
                }
            }

            // Stats column
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                RingLegendItem(
                    color = AquaCyan,
                    label = "Hidrasi",
                    value = "${state.todayTotalMl}",
                    target = "/${state.targetMl} ml"
                )
                RingLegendItem(
                    color = NeonGreen,
                    label = "Gelas",
                    value = "${state.cupsConsumed}",
                    target = "/${state.totalCupsTarget} gls"
                )
                RingLegendItem(
                    color = PurpleAccent,
                    label = "Streak",
                    value = "${state.streakDays}",
                    target = "/30 hr"
                )
            }
        }

        // "Sisa" label at bottom
        Spacer(Modifier.height(12.dp))
    }

    // Remaining label
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
    ) {
        if (state.progressFraction >= 1f) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(NeonGreen.copy(alpha = 0.15f))
                    .border(1.dp, NeonGreen.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🎉", fontSize = 20.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Target Hari Ini Tercapai!",
                    color = NeonGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AquaCyan.copy(alpha = 0.08f))
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.WaterDrop, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Sisa kebutuhan", color = TextSecondary, fontSize = 13.sp)
                }
                Text(
                    "${state.remainingMl} ml",
                    color = AquaCyan,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun RingLegendItem(color: Color, label: String, value: String, target: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Column {
            Text(label, color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, color = color, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Text(target, color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(bottom = 2.dp))
            }
        }
    }
}

// ─── Quick Log Section ────────────────────────────────────────────────────────
@Composable
private fun QuickLogSection(
    glassSizeMl: Int,
    onLog: (Int) -> Unit,
    onCustom: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Text(
            "Catat Minum",
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val presets = listOf(
            Triple(150, "☕", "Espresso"),
            Triple(glassSizeMl, "🥛", "${glassSizeMl}ml"),
            Triple(350, "🧴", "350ml"),
            Triple(500, "🍶", "Botol"),
            Triple(600, "💧", "600ml"),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            presets.take(4).forEach { (amount, emoji, label) ->
                QuickLogChip(
                    emoji = emoji,
                    label = label,
                    amount = amount,
                    onClick = { onLog(amount) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        // Full-width custom button
        OutlinedButton(
            onClick = onCustom,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, AquaCyan.copy(alpha = 0.4f)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = AquaCyan)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text("Jumlah Kustom", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun QuickLogChip(
    emoji: String,
    label: String,
    amount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 0.94f else 1f, label = "chipScale")

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CardDark),
        contentPadding = PaddingValues(4.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 22.sp)
            Text(
                label,
                color = TextSecondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                "+${amount}ml",
                color = AquaCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

// ─── Hydration Stats Row ──────────────────────────────────────────────────────
@Composable
private fun HydrationStatsRow(state: DashboardUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            icon = "🏆",
            label = "Streak",
            value = "${state.streakDays}",
            unit = "hari",
            accentColor = OrangeAccent,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = "🥛",
            label = "Gelas Hari Ini",
            value = "${state.cupsConsumed}",
            unit = "gelas",
            accentColor = NeonGreen,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = "🎯",
            label = "Target",
            value = "${state.targetMl}",
            unit = "ml",
            accentColor = PurpleAccent,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    icon: String,
    label: String,
    value: String,
    unit: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .border(1.dp, accentColor.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(icon, fontSize = 20.sp)
        Spacer(Modifier.height(6.dp))
        Text(value, color = accentColor, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
        Text(unit, color = TextSecondary, fontSize = 11.sp)
        Spacer(Modifier.height(2.dp))
        Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

// ─── Weekly Bar Chart Card ────────────────────────────────────────────────────
@Composable
private fun WeeklyChartCard(
    weeklyTotals: List<Int>,
    targetMl: Int,
    onOpenHistory: () -> Unit
) {
    val dayLabels = listOf("Sen","Sel","Rab","Kam","Jum","Sab","Min")
    val cal = Calendar.getInstance()
    val todayDow = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7 // 0=Mon

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(CardDark)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("7 Hari Terakhir", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            TextButton(onClick = onOpenHistory, contentPadding = PaddingValues(0.dp)) {
                Text("Lihat semua", color = AquaCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = AquaCyan, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(Modifier.height(16.dp))

        // Bar chart
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            val maxVal = weeklyTotals.maxOrNull()?.coerceAtLeast(targetMl) ?: targetMl
            weeklyTotals.take(7).forEachIndexed { index, total ->
                val fraction = if (maxVal == 0) 0f else total.toFloat() / maxVal
                val isToday = index == 0
                val reached = total >= targetMl
                val barColor = when {
                    isToday -> AquaCyan
                    reached -> NeonGreen
                    else -> TrackGray
                }
                val animFraction by animateFloatAsState(fraction, tween(800, index * 80), label = "bar$index")
                val dayOffset = (todayDow - index + 7) % 7
                val dayLabel = dayLabels[dayOffset]

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height((animFraction * 80).dp.coerceAtLeast(4.dp))
                            .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            .background(
                                if (isToday) Brush.verticalGradient(listOf(AquaCyan, AquaBlue))
                                else Brush.verticalGradient(listOf(barColor, barColor.copy(alpha = 0.6f)))
                            )
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        dayLabel,
                        color = if (isToday) AquaCyan else TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        // Target line label
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(AquaCyan))
            Text("Hari ini", color = TextSecondary, fontSize = 11.sp)
            Spacer(Modifier.width(8.dp))
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(NeonGreen))
            Text("Target tercapai", color = TextSecondary, fontSize = 11.sp)
        }
    }
}

// ─── Today's Log ─────────────────────────────────────────────────────────────
@Composable
private fun TodayLogHeader(count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Catatan Hari Ini", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        if (count > 0) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(AquaBlue.copy(alpha = 0.2f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text("$count catatan", color = AquaCyan, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun EmptyLogCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardDark)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("💧", fontSize = 32.sp)
            Spacer(Modifier.height(8.dp))
            Text("Belum ada catatan hari ini", color = TextSecondary, fontSize = 14.sp, textAlign = TextAlign.Center)
            Text("Ayo mulai minum air!", color = AquaCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogEntryRow(
    entry: com.aquareminder.data.WaterEntry,
    onDelete: () -> Unit
) {
    val sdf = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { it == SwipeToDismissBoxValue.EndToStart }
    )
    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            onDelete()
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 3.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFFF453A).copy(alpha = 0.8f)),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier.padding(end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = Color.White)
                    Text("Hapus", color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 3.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(CardDark)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AquaBlue.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💧", fontSize = 18.sp)
                }
                Column {
                    Text("+${entry.amountMl} ml", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(sdf.format(Date(entry.timestamp)), color = TextSecondary, fontSize = 12.sp)
                }
            }
            Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = TextSecondary.copy(alpha = 0.3f), modifier = Modifier.size(16.dp))
        }
    }
}
