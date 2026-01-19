package com.arathort.growbox.domain.repository

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.MonthlyLog

interface AnalyticsRepository {
    suspend fun getDailyLogs(deviceId: String, startDate: String, endDate: String): DailyLog

    suspend fun getLastWeekLogs(deviceId: String): List<DailyLog>
    suspend fun getMonthlyLogs(deviceId: String, year: String): List<MonthlyLog>
}