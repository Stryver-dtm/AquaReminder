package com.aquareminder.ui

import androidx.compose.animation.core.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*

// Onboarding Color palette (matches Dashboard)
private val OBBgDark       = Color(0xFF0A0E1A)
private val OBCardDark     = Color(0xFF141824)
private val OBCardDark2    = Color(0xFF1C2235)
private val OBAquaCyan     = Color(0xFF00D4FF)
private val OBAquaBlue     = Color(0xFF0066FF)
private val OBTextPrimary  = Color(0xFFFFFFFF)
private val OBTextSecondary= Color(0xFF8B9DC3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    onFinish: (targetMl: Int) -> Unit,
    onSkip: () -> Unit
) {
    var weightText by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf(1) } // 0=Rendah, 1=Sedang, 2=Tinggi

    val recommendedTarget = remember(weightText, activityLevel) {
        val w = weightText.toIntOrNull() ?: 0
        if (w == 0) 2000
        else {
            val base = w * 33
            val offset = when (activityLevel) { 0 -> -200; 2 -> 500; else -> 0 }
            (base + offset).coerceIn(1500, 4000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OBBgDark)
    ) {
        // Decorative gradient circles
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-80).dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(listOf(OBAquaBlue.copy(alpha = 0.25f), Color.Transparent)),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(250.dp)
                .offset(x = 150.dp, y = 500.dp)
                .background(
                    Brush.radialGradient(listOf(OBAquaCyan.copy(alpha = 0.15f), Color.Transparent)),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top: Skip
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onSkip) {
                    Text("Lewati", color = OBTextSecondary, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Middle content
            Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
                // App icon
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Brush.linearGradient(listOf(OBAquaBlue, OBAquaCyan))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💧", fontSize = 36.sp)
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Selamat Datang di", color = OBTextSecondary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("AquaReminder", color = OBTextPrimary, fontSize = 34.sp, fontWeight = FontWeight.ExtraBold)
                    Text(
                        "Ayo hitung kebutuhan air harianmu dan mulai kebiasaan hidrasi yang sehat.",
                        color = OBTextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }

                // Weight input
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Berat Badanmu", color = OBTextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    OutlinedTextField(
                        value = weightText,
                        onValueChange = { weightText = it.filter { c -> c.isDigit() }.take(3) },
                        placeholder = { Text("Contoh: 65", color = OBTextSecondary) },
                        suffix = { Text("kg", color = OBTextSecondary) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = OBTextPrimary,
                            unfocusedTextColor = OBTextPrimary,
                            focusedContainerColor = OBCardDark,
                            unfocusedContainerColor = OBCardDark,
                            focusedBorderColor = OBAquaCyan,
                            unfocusedBorderColor = OBCardDark2,
                            cursorColor = OBAquaCyan
                        )
                    )
                }

                // Activity level
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Tingkat Aktivitas", color = OBTextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OBActivityChip("🧘", "Rendah", 0, activityLevel) { activityLevel = 0 }
                        OBActivityChip("🚶", "Sedang", 1, activityLevel) { activityLevel = 1 }
                        OBActivityChip("🏃", "Tinggi", 2, activityLevel) { activityLevel = 2 }
                    }
                }

                // Recommendation box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.linearGradient(listOf(OBAquaBlue.copy(alpha = 0.2f), OBAquaCyan.copy(alpha = 0.1f))))
                        .border(1.dp, OBAquaCyan.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                        .padding(20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Text("Target Air Harianmu", color = OBTextSecondary, fontSize = 13.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("$recommendedTarget ml", color = OBAquaCyan, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                        Text("≈ ${recommendedTarget / 250} gelas per hari", color = OBTextSecondary, fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            // Bottom CTA
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { onFinish(recommendedTarget) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(listOf(OBAquaBlue, OBAquaCyan))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Mulai Sekarang", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    }
                }
                TextButton(onClick = onSkip, modifier = Modifier.fillMaxWidth()) {
                    Text("Pakai Target Default (2000ml)", color = OBTextSecondary, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun RowScope.OBActivityChip(
    emoji: String,
    label: String,
    level: Int,
    selected: Int,
    onClick: () -> Unit
) {
    val isSelected = level == selected
    Button(
        onClick = onClick,
        modifier = Modifier.weight(1f).height(72.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) OBAquaBlue.copy(alpha = 0.25f) else OBCardDark),
        border = if (isSelected) BorderStroke(1.5.dp, OBAquaCyan) else BorderStroke(1.dp, OBCardDark2),
        contentPadding = PaddingValues(4.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 22.sp)
            Text(label, color = if (isSelected) OBAquaCyan else OBTextSecondary, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
        }
    }
}
