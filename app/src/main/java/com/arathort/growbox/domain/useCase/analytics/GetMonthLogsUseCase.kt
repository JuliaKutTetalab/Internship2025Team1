package com.arathort.growbox.domain.useCase.analytics

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.repository.AnalyticsRepository
import jakarta.inject.Inject

class GetMonthLogsUseCase @Inject constructor(private val repository: AnalyticsRepository) {
    suspend operator fun invoke(deviceId: String): List<DailyLog> {
        return repository.getLastMonthLogs(deviceId)
    }
}