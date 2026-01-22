package com.arathort.growbox.domain.useCase.analytics

import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.repository.AnalyticsRepository
import javax.inject.Inject

class GetTodayLogUseCase @Inject constructor(
    private val repository: AnalyticsRepository
) {
    suspend operator fun invoke(deviceId: String): DailyLog? {
        return repository.getTodayLog(deviceId)
    }
}