package com.arathort.growbox.domain.repository

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.MonthlyLog

interface AnalyticsRepository {
    suspend fun getTodayLog(deviceId: String): DailyLog?
    suspend fun getLastWeekLogs(deviceId: String): List<DailyLog>
    suspend fun getLastMonthLogs(deviceId: String): List<DailyLog>
}