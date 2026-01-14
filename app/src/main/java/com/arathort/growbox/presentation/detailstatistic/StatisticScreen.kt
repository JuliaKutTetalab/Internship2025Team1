package com.arathort.growbox.presentation.detailstatistic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arathort.growbox.presentation.home.SensorType

@Composable
fun StatisticScreen(sensorType: SensorType) {


    Text(text = sensorType.name)
}