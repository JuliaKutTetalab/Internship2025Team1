package com.arathort.growbox.data.repository

import com.arathort.growbox.data.remote.dto.analytics.DailyLogDto
import com.arathort.growbox.data.remote.dto.analytics.toDomain
import com.arathort.growbox.data.repository.common.Collections
import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.repository.AnalyticsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AnalyticsRepository {

    override suspend fun getTodayLog(deviceId: String): DailyLog? {
        val today = LocalDate.now().toString()

        return getDailyLogByDate(deviceId, today)
    }

    override suspend fun getLastWeekLogs(deviceId: String): List<DailyLog> {
        val today = LocalDate.now()
        val start = today.minusDays(6)

        return getLogsByRange(deviceId, start.toString(), today.toString())
    }

    override suspend fun getLastMonthLogs(deviceId: String): List<DailyLog> {
        val today = LocalDate.now()
        val start = today.minusDays(30)

        return getLogsByRange(deviceId, start.toString(), today.toString())
    }

    private suspend fun getDailyLogByDate(deviceId: String, date: String): DailyLog? {
        return try {
            firestore.collection(Collections.analytics)
                .whereEqualTo(Collections.deviceId, deviceId)
                .whereEqualTo(Collections.date, date)
                .limit(1)
                .get()
                .await()
                .documents
                .firstOrNull()
                ?.toObject(DailyLogDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun getLogsByRange(
        deviceId: String,
        startDate: String,
        endDate: String
    ): List<DailyLog> {
        return try {
            firestore.collection(Collections.analytics)
                .whereEqualTo(Collections.deviceId, deviceId)
                .whereGreaterThanOrEqualTo(Collections.date, startDate)
                .whereLessThanOrEqualTo(Collections.date, endDate)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(DailyLogDto::class.java)?.toDomain() }
                .sortedBy { it.date }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}