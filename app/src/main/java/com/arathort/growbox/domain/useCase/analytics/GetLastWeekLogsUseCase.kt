package com.arathort.growbox.domain.useCase.analytics

import com.arathort.growbox.domain.models.analytics.MonthlyLog
import com.arathort.growbox.domain.repository.AnalyticsRepository
import javax.inject.Inject

class GetLastWeekLogsUseCase @Inject constructor(private val repository: AnalyticsRepository) {
    suspend operator fun invoke(deviceId: String, year: String): List<MonthlyLog> {
        return repository.getMonthlyLogs(deviceId, year)
    }
}