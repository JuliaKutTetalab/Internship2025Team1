package com.arathort.growbox.data.remote.dto.analytics

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.HourlyReading

data class DailyLogDto(
    val id: String,

    val device_id: String,

    val date: String,

    val hourly_readings: List<HourlyReadingDto>,

    val min_temp: Double,
    val max_temp: Double,
    val min_humidity: Double,
    val max_humidity: Double,
    val total_power_kwh: Double,
    val total_water_ml: Double,
    val total_nutrition_mg: Double
)

data class HourlyReadingDto(
    val timestamp: Long,
    val temperature: Double,
    val humidity: Double,
    val light: Double,
    val nutrition: Double
)

fun DailyLogDto.toDomain(): DailyLog {
    return DailyLog(
        id = id,
        deviceId = device_id,
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

fun DailyLog.toDto(): DailyLogDto {
    return DailyLogDto(
        id = id,
        device_id = deviceId,
        date = date,
        hourly_readings = hourlyReadings.map { it.toDto() },
        min_temp = minTemp,
        max_temp = maxTemp,
        min_humidity = minHumidity,
        max_humidity = maxHumidity,
        total_power_kwh = totalPowerKwh,
        total_water_ml = totalWaterMl,
        total_nutrition_mg = totalNutritionMg
    )
}

fun HourlyReadingDto.toDomain(): HourlyReading {
    return HourlyReading(
        timestamp = timestamp,
        temperature = temperature,
        humidity = humidity,
        light = light,
        nutrition = nutrition
    )
}

fun HourlyReading.toDto(): HourlyReadingDto {
    return HourlyReadingDto(
        timestamp = timestamp,
        temperature = temperature,
        humidity = humidity,
        light = light,
        nutrition = nutrition
    )
}