package com.arathort.growbox.presentation.deviceconnection.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arathort.growbox.ui.theme.Green800

@Composable
fun PulsatingRadar(
    modifier: Modifier = Modifier,
    color: Color = Green800,
    durationMillis: Int = 3000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = this.center
            val maxRadius = size.minDimension * 0.75f

            val waves = listOf(0.33f, 0.66f, 0.99f)

            waves.forEach { offset ->
                val currentProgress = (progress + offset) % 1f

                val radius = maxRadius * currentProgress

                val alpha = (1f - currentProgress)

                drawCircle(
                    color = color.copy(alpha = alpha),
                    radius = radius,
                    center = center
                )
            }

            drawCircle(
                color = color,
                radius = 25.dp.toPx(),
                center = center
            )
        }
    }
}