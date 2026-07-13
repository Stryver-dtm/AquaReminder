package com.aquareminder.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aquareminder.ui.theme.AquaGradientEnd
import com.aquareminder.ui.theme.AquaGradientStart
import com.aquareminder.ui.theme.DeepAqua
import com.aquareminder.ui.theme.IceWhite
import com.aquareminder.ui.theme.MistBlue
import com.aquareminder.ui.theme.SunlitCoral

@Composable
fun AquaScreenBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        IceWhite,
                        MistBlue,
                        Color(0xFFEAF7F2)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun AquaProgressRing(
    progress: Float,
    totalMl: Int,
    targetMl: Int,
    modifier: Modifier = Modifier,
    size: Dp = 230.dp
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 900),
        label = "hydrationProgress"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 20.dp.toPx()
            val inset = strokeWidth / 2
            val arcSize = Size(width = this.size.width - strokeWidth, height = this.size.height - strokeWidth)

            drawCircle(
                color = Color.White.copy(alpha = 0.72f),
                radius = this.size.minDimension / 2f,
                center = center
            )
            drawArc(
                color = AquaGradientStart.copy(alpha = 0.14f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(AquaGradientEnd, AquaGradientStart, DeepAqua, AquaGradientEnd),
                    center = center
                ),
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                topLeft = Offset(inset, inset),
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$totalMl",
                fontSize = 42.sp,
                lineHeight = 46.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "ml dari $targetMl ml",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.62f),
                style = MaterialTheme.typography.bodyLarge
            )
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(CircleShape)
                    .background(AquaGradientStart.copy(alpha = 0.12f))
                    .padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    color = DeepAqua,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MetricTile(
    title: String,
    value: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.82f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.18f))
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

data class DailyHydration(
    val dayLabel: String,
    val dateLabel: String,
    val totalMl: Int,
    val progressFraction: Float,
    val isToday: Boolean
)

@Composable
fun SevenDayBarChart(
    history: List<DailyHydration>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        history.forEach { day ->
            val animatedProgress by animateFloatAsState(
                targetValue = day.progressFraction,
                animationSpec = tween(durationMillis = 700),
                label = "bar${day.dateLabel}"
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "${day.totalMl}",
                    fontSize = 11.sp,
                    lineHeight = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.58f),
                    textAlign = TextAlign.Center
                )
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth()
                        .height(112.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(AquaGradientStart.copy(alpha = 0.11f)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(animatedProgress.coerceAtLeast(0.04f))
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.verticalGradient(
                                    colors = if (day.isToday) {
                                        listOf(SunlitCoral, AquaGradientStart)
                                    } else {
                                        listOf(AquaGradientEnd, DeepAqua)
                                    }
                                )
                            )
                    )
                }
                Text(
                    text = day.dayLabel.take(3),
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (day.isToday) 0.92f else 0.62f),
                    fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}
