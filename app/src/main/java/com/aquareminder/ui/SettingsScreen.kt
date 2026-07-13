package com.aquareminder.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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

private val SBgDark        = Color(0xFF0A0E1A)
private val SCardDark      = Color(0xFF141824)
private val SCardDark2     = Color(0xFF1C2235)
private val SAquaCyan      = Color(0xFF00D4FF)
private val SAquaBlue      = Color(0xFF0066FF)
private val STextPrimary   = Color(0xFFFFFFFF)
private val STextSecondary = Color(0xFF8B9DC3)
private val STrackGray     = Color(0xFF252A3A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: DashboardViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val s = state.settings

    var targetMlText by remember(s.dailyTargetMl) { mutableStateOf(s.dailyTargetMl.toString()) }
    var glassSizeText by remember(s.glassSizeMl) { mutableStateOf(s.glassSizeMl.toString()) }
    var intervalText by remember(s.reminderIntervalMinutes) { mutableStateOf(s.reminderIntervalMinutes.toString()) }
    var startHour by remember(s.activeStartHour) { mutableStateOf(s.activeStartHour.toFloat()) }
    var endHour by remember(s.activeEndHour) { mutableStateOf(s.activeEndHour.toFloat()) }
    var reminderEnabled by remember(s.isReminderEnabled) { mutableStateOf(s.isReminderEnabled) }

    fun saveAll() {
        viewModel.updateSettings(
            s.copy(
                dailyTargetMl = targetMlText.toIntOrNull() ?: 2000,
                glassSizeMl = glassSizeText.toIntOrNull() ?: 250,
                reminderIntervalMinutes = intervalText.toIntOrNull() ?: 90,
                activeStartHour = startHour.toInt(),
                activeEndHour = endHour.toInt(),
                isReminderEnabled = reminderEnabled
            )
        )
    }

    Scaffold(
        containerColor = SBgDark,
        topBar = {
            TopAppBar(
                title = { Text("Pengaturan", color = STextPrimary, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = SAquaCyan)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SBgDark)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(top = padding.calculateTopPadding() + 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsCard(title = "🎯 Target Hidrasi") {
                SettingsTextField(label = "Target Harian", value = targetMlText, onValueChange = { targetMlText = it.filter { c -> c.isDigit() } }, suffix = "ml")
                Spacer(Modifier.height(8.dp))
                Text("Preset cepat", color = STextSecondary, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(1500, 2000, 2500, 3000).forEach { preset ->
                        val isSel = targetMlText == preset.toString()
                        FilterChip(
                            selected = isSel,
                            onClick = { targetMlText = preset.toString() },
                            label = { Text("${preset}ml", fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SAquaBlue.copy(alpha = 0.3f), selectedLabelColor = SAquaCyan, containerColor = SCardDark2, labelColor = STextSecondary),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = isSel, selectedBorderColor = SAquaCyan, borderColor = STrackGray)
                        )
                    }
                }
            }

            SettingsCard(title = "🥛 Ukuran Gelas") {
                SettingsTextField(label = "Ukuran Gelas/Botol", value = glassSizeText, onValueChange = { glassSizeText = it.filter { c -> c.isDigit() } }, suffix = "ml")
                Spacer(Modifier.height(8.dp))
                Text("Preset cepat", color = STextSecondary, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(150, 200, 250, 500).forEach { preset ->
                        val isSel = glassSizeText == preset.toString()
                        FilterChip(
                            selected = isSel,
                            onClick = { glassSizeText = preset.toString() },
                            label = { Text("${preset}ml", fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SAquaBlue.copy(alpha = 0.3f), selectedLabelColor = SAquaCyan, containerColor = SCardDark2, labelColor = STextSecondary),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = isSel, selectedBorderColor = SAquaCyan, borderColor = STrackGray)
                        )
                    }
                }
            }

            SettingsCard(title = "🔔 Pengingat") {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Aktifkan Pengingat", color = STextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text("Terima notifikasi berkala", color = STextSecondary, fontSize = 12.sp)
                    }
                    Switch(checked = reminderEnabled, onCheckedChange = { reminderEnabled = it }, colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = SAquaBlue, uncheckedTrackColor = STrackGray))
                }
                if (reminderEnabled) {
                    Spacer(Modifier.height(12.dp))
                    SettingsTextField(label = "Interval Pengingat", value = intervalText, onValueChange = { intervalText = it.filter { c -> c.isDigit() } }, suffix = "menit")
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(30, 60, 90, 120).forEach { preset ->
                            val isSel = intervalText == preset.toString()
                            FilterChip(
                                selected = isSel,
                                onClick = { intervalText = preset.toString() },
                                label = { Text("${preset}m", fontSize = 12.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = SAquaBlue.copy(alpha = 0.3f), selectedLabelColor = SAquaCyan, containerColor = SCardDark2, labelColor = STextSecondary),
                                border = FilterChipDefaults.filterChipBorder(enabled = true, selected = isSel, selectedBorderColor = SAquaCyan, borderColor = STrackGray)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Jam Aktif", color = STextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Mulai: ${startHour.toInt()}:00", color = SAquaCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Selesai: ${endHour.toInt()}:00", color = SAquaCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Text("Jam Mulai", color = STextSecondary, fontSize = 12.sp)
                    Slider(value = startHour, onValueChange = { startHour = it.toInt().toFloat() }, valueRange = 5f..12f, steps = 6, colors = SliderDefaults.colors(thumbColor = SAquaCyan, activeTrackColor = SAquaBlue, inactiveTrackColor = STrackGray))
                    Text("Jam Selesai", color = STextSecondary, fontSize = 12.sp)
                    Slider(value = endHour, onValueChange = { endHour = it.toInt().toFloat() }, valueRange = 18f..24f, steps = 5, colors = SliderDefaults.colors(thumbColor = SAquaCyan, activeTrackColor = SAquaBlue, inactiveTrackColor = STrackGray))
                }
            }

            Button(
                onClick = { saveAll() },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(SAquaBlue, SAquaCyan))), contentAlignment = Alignment.Center) {
                    Text("Simpan Pengaturan", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
private fun SettingsCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(SCardDark).padding(20.dp)) {
        Text(title, color = STextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(Modifier.height(16.dp))
        content()
    }
}

@Composable
private fun SettingsTextField(label: String, value: String, onValueChange: (String) -> Unit, suffix: String) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label, color = STextSecondary, fontSize = 12.sp) },
        suffix = { Text(suffix, color = STextSecondary) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = STextPrimary, unfocusedTextColor = STextPrimary,
            focusedContainerColor = SCardDark2, unfocusedContainerColor = SCardDark2,
            focusedBorderColor = SAquaCyan, unfocusedBorderColor = STrackGray,
            cursorColor = SAquaCyan, focusedLabelColor = SAquaCyan, unfocusedLabelColor = STextSecondary
        )
    )
}
