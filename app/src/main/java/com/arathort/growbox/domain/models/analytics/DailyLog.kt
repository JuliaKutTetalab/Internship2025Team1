package com.arathort.growbox.domain.models.analytics

data class DailyLog(
    val id: String,
    val deviceId: String,
    val date: String,
    val hourlyReadings: List<HourlyReading>,
    val minTemp: Double,
    val maxTemp: Double,
    val minHumidity: Double,
    val maxHumidity: Double,
    val totalPowerKwh: Double,
    val totalWaterMl: Double,
    val totalNutritionMg: Double
)

data class HourlyReading(
    val timestamp: Long,
    val temperature: Double,
    val humidity: Double,
    val light: Double,
    val nutrition: Double
)