package com.arathort.growbox.presentation.history.chart

import com.arathort.growbox.presentation.chart.ChartPoint
import com.arathort.growbox.presentation.chart.StatisticPeriod

data class HistoryChartUiState(
    val isLoading: Boolean = false,
    val screenTitle: Int = 0,
    val sensorIcon: Int = 0,
    val unit: Int=0,
    val unitCalculate: Int=0,
    val currentValue: String = "",
    val selectedPeriod: StatisticPeriod = StatisticPeriod.WEEK,
    val chartData: List<ChartPoint> = emptyList(),
    val statLowestValue: String = "--",
    val statLowestDate: String = "",
    val statHighestValue: String = "--",
    val statHighestDate: String = "")

