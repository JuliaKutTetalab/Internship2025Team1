package com.arathort.growbox.presentation.chart

data class ChartUiState(
    val isLoading: Boolean = false,
    val screenTitle: Int = 0,
    val sensorIcon: Int = 0,
    val unit: Int=0,
    val unitCalculate: Int=0,
    val currentValue: String = "",
    val selectedPeriod: StatisticPeriod = StatisticPeriod.WEEK,
    val chartData: List<ChartPoint> = emptyList(),
    val statCurrent: String = "",
    val statRecommended: String = "",
    val statPeriodValue: String = "",
    val statTotalValue: String = ""
)

enum class StatisticPeriod(val label: String) {
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month")
}

data class ChartPoint(
    val label: String,
    val value: Float
)