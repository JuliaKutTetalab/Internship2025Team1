package com.arathort.growbox.data.remote.dto.analytics

import com.arathort.growbox.domain.models.analytics.DailySummary
import com.arathort.growbox.domain.models.analytics.MonthlyLog

data class MonthlyLogDto(

    val month_id: String = "",

    val daily_summaries: List<DailySummaryDto> = emptyList(),

    val min_temp_month: Double = 0.0,

    val max_temp_month: Double = 0.0,

    val avg_humidity_month: Double = 0.0,

    val max_light_month: Double = 0.0,

    val total_power_month_kwh: Double = 0.0,

    val total_water_month_ml: Double = 0.0,

    val total_nutrition_month_mg: Double = 0.0

)

data class DailySummaryDto(

    val date: String = "",

    val day_of_month: Int = 0,

    val avg_temp: Double = 0.0,

    val avg_humidity: Double = 0.0,

    val avg_light: Double = 0.0,

    val avg_nutrition: Double = 0.0,

    val daily_power_kwh: Double = 0.0,

    val daily_water_ml: Double = 0.0

)
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

fun MonthlyLogDto.toDomain(): MonthlyLog {
    return MonthlyLog(
        monthId = month_id,
        dailySummaries = daily_summaries.map { it.toDomain() },
        minTemp = min_temp_month,
        maxTemp = max_temp_month,
        avgHumidity = avg_humidity_month,
        maxLight = max_light_month,
        totalPowerKwh = total_power_month_kwh,
        totalWaterMl = total_water_month_ml,
        totalNutritionMg = total_nutrition_month_mg
    )
}