package com.arathort.growbox.presentation.chart

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.arathort.growbox.presentation.home.SensorType

@Composable
fun ChartScreen(
    sensorType: SensorType,
    chartViewModel: ChartViewModel= hiltViewModel()
) {
    Text(text = sensorType.name)
}