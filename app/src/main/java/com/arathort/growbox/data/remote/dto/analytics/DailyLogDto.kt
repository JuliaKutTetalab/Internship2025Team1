package com.arathort.growbox.data.remote.dto.analytics

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.HourlyReading

data class DailyLogDto(

    val date: String = "",

    val hourly_readings: List<HourlyReadingDto> = emptyList(),

    val min_temp: Double = 0.0,

    val max_temp: Double = 0.0,

    val min_humidity: Double = 0.0,

    val max_humidity: Double = 0.0,

    val total_power_kwh: Double = 0.0,

    val total_water_ml: Double = 0.0,

    val total_nutrition_mg: Double = 0.0

)

data class HourlyReadingDto(

    val timestamp: Long = 0,

    val temp: Double = 0.0,

    val hum: Double = 0.0,

    val light: Double = 0.0,

    val nutrition: Double = 0.0

)
fun HourlyReadingDto.toDomain(): HourlyReading {
    return HourlyReading(
        timestamp = timestamp,
        temperature = temp,
        humidity = hum,
        light = light,
        nutrition = nutrition
    )
}

fun DailyLogDto.toDomain(): DailyLog {
    return DailyLog(
        date = date,
        hourlyReadings = hourly_readings.map { it.toDomain() },
        minTemp = min_temp,
        maxTemp = max_temp,
        minHumidity = min_humidity,
        maxHumidity = max_humidity,
        totalPowerKwh = total_power_kwh,
        totalWaterMl = total_water_ml,
        totalNutritionMg = total_nutrition_mg
    )
}