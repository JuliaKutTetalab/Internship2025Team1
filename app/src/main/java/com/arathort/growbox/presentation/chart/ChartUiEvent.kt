package com.arathort.growbox.presentation.chart

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.DailySummary
import com.arathort.growbox.domain.models.analytics.HourlyReading
import com.arathort.growbox.domain.models.analytics.MonthlyLog


sealed interface ChartUiEvent {
    data object OnDailyGraphicSelected : ChartUiEvent
    data object OnWeekGraphicSelected : ChartUiEvent
    data object OnMonthlyGraphicSelected : ChartUiEvent

    data object OnReturnButtonClick : ChartUiEvent
}

object AnalyticsMocks {

    val defaultHourlyReadings = List(24) { index ->
        HourlyReading(
            timestamp = System.currentTimeMillis() - ((24 - index) * 3600 * 1000),
            temperature = 22.0 + (index % 4),
            humidity = 55.0 + (index % 5),
            light = if (index in 8..20) 100.0 else 0.0,
            nutrition = 1200.0 + (index % 10)
        )
    }

    val defaultDailyLog = DailyLog(
        id = "daily_log_001",
        deviceId = "device_01",
        date = "2023-10-25",
        hourlyReadings = defaultHourlyReadings,

        minTemp = 22.0,
        maxTemp = 26.0,
        minHumidity = 55.0,
        maxHumidity = 60.0,

        totalPowerKwh = 3.5,
        totalWaterMl = 450.0,
        totalNutritionMg = 120.0
    )

    val defaultDailySummaries = List(30) { index ->
        DailySummary(
            date = "2023-10-${index + 1}",
            dayOfMonth = index + 1,

            avgTemp = 24.0 + (index % 2),
            avgHumidity = 58.0 + (index % 3),
            avgLight = 80.0, // %
            avgNutrition = 1150.0,

            dailyPowerKwh = 3.2,
            dailyWaterMl = 500.0
        )
    }

    val defaultMonthlyLog = MonthlyLog(
        id = "monthly_log_001",
        deviceId = "device_01",
        monthId = "2023-10",

        dailySummaries = defaultDailySummaries,

        minTemp = 20.0,
        maxTemp = 28.0,
        avgHumidity = 60.0,
        maxLight = 100.0,

        totalPowerKwh = 96.5,
        totalWaterMl = 15000.0,
        totalNutritionMg = 3600.0
    )
}