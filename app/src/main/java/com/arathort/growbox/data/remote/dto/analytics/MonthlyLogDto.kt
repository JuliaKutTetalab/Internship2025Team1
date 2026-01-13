package com.arathort.growbox.data.remote.dto.analytics

import com.arathort.growbox.domain.models.analytics.DailySummary
import com.arathort.growbox.domain.models.analytics.MonthlyLog

data class MonthlyLogDto(
    val id: String,

    val device_id: String,

    val month_id: String,

    val daily_summaries: List<DailySummaryDto>,

    val min_temp: Double,
    val max_temp: Double,
    val avg_humidity: Double,
    val max_light: Double,
    val total_power_kwh: Double,
    val total_water_ml: Double,
    val total_nutrition_mg: Double
)

data class DailySummaryDto(
    val date: String,
    val day_of_month: Int,
    val avg_temp: Double,
    val avg_humidity: Double,
    val avg_light: Double,
    val avg_nutrition: Double,
    val daily_power_kwh: Double,
    val daily_water_ml: Double
)

fun MonthlyLogDto.toDomain(): MonthlyLog {
    return MonthlyLog(
        id = id,
        deviceId = device_id,
        monthId = month_id,
        dailySummaries = daily_summaries.map { it.toDomain() },
        minTemp = min_temp,
        maxTemp = max_temp,
        avgHumidity = avg_humidity,
        maxLight = max_light,
        totalPowerKwh = total_power_kwh,
        totalWaterMl = total_water_ml,
        totalNutritionMg = total_nutrition_mg
    )
}

fun MonthlyLog.toDto(): MonthlyLogDto {
    return MonthlyLogDto(
        id = id,
        device_id = deviceId,
        month_id = monthId,
        daily_summaries = dailySummaries.map { it.toDto() },
        min_temp = minTemp,
        max_temp = maxTemp,
        avg_humidity = avgHumidity,
        max_light = maxLight,
        total_power_kwh = totalPowerKwh,
        total_water_ml = totalWaterMl,
        total_nutrition_mg = totalNutritionMg
    )
}

fun DailySummaryDto.toDomain(): DailySummary {
    return DailySummary(
        date = date,
        dayOfMonth = day_of_month,
        avgTemp = avg_temp,
        avgHumidity = avg_humidity,
        avgLight = avg_light,
        avgNutrition = avg_nutrition,
        dailyPowerKwh = daily_power_kwh,
        dailyWaterMl = daily_water_ml
    )
}

fun DailySummary.toDto(): DailySummaryDto {
    return DailySummaryDto(
        date = date,
        day_of_month = dayOfMonth,
        avg_temp = avgTemp,
        avg_humidity = avgHumidity,
        avg_light = avgLight,
        avg_nutrition = avgNutrition,
        daily_power_kwh = dailyPowerKwh,
        daily_water_ml = dailyWaterMl
    )
}