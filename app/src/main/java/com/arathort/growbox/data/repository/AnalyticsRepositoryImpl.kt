package com.arathort.growbox.data.repository

import com.arathort.growbox.data.remote.dto.analytics.DailyLogDto
import com.arathort.growbox.data.remote.dto.analytics.MonthlyLogDto
import com.arathort.growbox.data.remote.dto.analytics.toDomain
import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.MonthlyLog
import com.arathort.growbox.domain.repository.AnalyticsRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : AnalyticsRepository {

    override suspend fun getDailyLogs(deviceId: String, startDate: String, endDate: String): List<DailyLog> {
        return try {
            firestore.collection("analytics_daily")
                .whereEqualTo("device_id", deviceId)
                .whereGreaterThanOrEqualTo("date", startDate)
                .whereLessThanOrEqualTo("date", endDate)
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
    override suspend fun getLastWeekLogs(deviceId: String): List<DailyLog> {

        val today = LocalDate.now()

        val weekAgo = today.minusDays(7)

        val startDateString = weekAgo.toString()
        val endDateString = today.toString()

        return getDailyLogs(deviceId, startDateString, endDateString)
    }
    override suspend fun getMonthlyLogs(deviceId: String, year: String): List<MonthlyLog> {
        return try {

            firestore.collection("analytics_monthly")
                .whereEqualTo("device_id", deviceId)
                .whereGreaterThanOrEqualTo("month_id", "$year-01")
                .whereLessThanOrEqualTo("month_id", "$year-12")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(MonthlyLogDto::class.java)?.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}