package com.arathort.growbox.domain.useCase.analytics

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.MonthlyLog
import com.arathort.growbox.domain.repository.AnalyticsRepository
import javax.inject.Inject

class GetDailyLogsUseCase @Inject constructor(private val repository: AnalyticsRepository) {
    suspend operator fun invoke(
        deviceId: String,
        startDate: String,
        endData: String
    ): DailyLog {
        return repository.getDailyLogs(deviceId)
    }
}