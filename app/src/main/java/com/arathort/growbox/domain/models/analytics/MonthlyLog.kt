package com.arathort.growbox.domain.models.analytics

data class MonthlyLog(

    val monthId: String,

    val dailySummaries: List<DailySummary>,

    val minTemp: Double,

    val maxTemp: Double,

    val avgHumidity: Double,

    val maxLight: Double,

    val totalPowerKwh: Double,

    val totalWaterMl: Double,

    val totalNutritionMg: Double

)

data class DailySummary(

    val date: String,

    val dayOfMonth: Int,

    val avgTemp: Double,

    val avgHumidity: Double,

    val avgLight: Double,

    val avgNutrition: Double,

    val dailyPowerKwh: Double,

    val dailyWaterMl: Double
)