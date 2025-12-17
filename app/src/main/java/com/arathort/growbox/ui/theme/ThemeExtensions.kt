package com.arathort.growbox.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class GrowBoxColors(
    val sensorValue: Color,
    val sensorLabel: Color,
    val sensorIcon: Color,
    val cardBackground: Color,
    val border: Color,
    val progressTrack: Color,
    val counterDay: Color
)

val LightCustomColors = GrowBoxColors(
    sensorValue = Green500,
    sensorLabel = Color.Black,
    sensorIcon = Green800,
    cardBackground = White,
    border = Grey300,
    progressTrack = Green200,
    counterDay=Grey400
)

val DarkCustomColors = GrowBoxColors(
    sensorValue = Green600,
    sensorLabel = White,
    sensorIcon = White,
    cardBackground = Color(0xFF1E1E1E),
    border = GreenGreyDark,
    progressTrack = White,
    counterDay=White
)

val LocalGrowBoxColors = staticCompositionLocalOf { LightCustomColors }