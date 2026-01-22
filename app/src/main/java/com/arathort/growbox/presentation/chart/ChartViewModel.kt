package com.arathort.growbox.presentation.chart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.models.analytics.DailyLog
import com.arathort.growbox.domain.models.analytics.HourlyReading
import com.arathort.growbox.domain.useCase.analytics.GetMonthLogsUseCase
import com.arathort.growbox.domain.useCase.analytics.GetTodayLogUseCase
import com.arathort.growbox.domain.useCase.analytics.GetWeekLogsUseCase
import com.arathort.growbox.domain.useCase.device.GetDeviceSettingsUseCase
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import com.arathort.growbox.presentation.home.SensorType
import com.arathort.growbox.presentation.home.defaultState
import com.arathort.growbox.presentation.settings.defaultSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getDeviceSettingsUseCase: GetDeviceSettingsUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
    private val getTodayLogUseCase: GetTodayLogUseCase,
    private val getWeekLogsUseCase: GetWeekLogsUseCase,
    private val getMonthLogsUseCase: GetMonthLogsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChartUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<ChartUiEffect>()
    val effect = _effect.receiveAsFlow()

    private var sensorType: SensorType? = null
    private val deviceId = "1"

    fun initSensorType(typeName: String) {
        try {
            val newType = SensorType.valueOf(typeName)
            if (sensorType == null || sensorType != newType) {
                sensorType = newType
                configureUi()
                loadData()
                loadGraphData(uiState.value.selectedPeriod)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onEvent(event: ChartUiEvent) {
        when (event) {
            is ChartUiEvent.OnDailyGraphicSelected -> {
                _uiState.update { it.copy(selectedPeriod = StatisticPeriod.DAY) }
                loadGraphData(StatisticPeriod.DAY)
            }
            is ChartUiEvent.OnWeekGraphicSelected -> {
                _uiState.update { it.copy(selectedPeriod = StatisticPeriod.WEEK) }
                loadGraphData(StatisticPeriod.WEEK)
            }
            is ChartUiEvent.OnMonthlyGraphicSelected -> {
                _uiState.update { it.copy(selectedPeriod = StatisticPeriod.MONTH) }
                loadGraphData(StatisticPeriod.MONTH)
            }
            is ChartUiEvent.OnReturnButtonClick -> {
                viewModelScope.launch {
                    _effect.send(ChartUiEffect.onNavigateToBack)
                }
            }
            else -> {}
        }
    }

    private fun configureUi() {
        val type = sensorType ?: return

        _uiState.update {
            it.copy(
                screenTitle = type.title,
                sensorIcon = type.icon,
                unit = type.unit,
                unitCalculate = type.unitCalculate,
            )
        }
    }

    private fun loadData() {
        val type = sensorType ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val settings = getDeviceSettingsUseCase(deviceId) ?: defaultSettings
                val deviseState = getDeviceStateUseCase(deviceId) ?: defaultState

                var currentValueStr = ""
                var recommendedStr = ""

                when (type) {
                    SensorType.LIGHT -> {
                        currentValueStr = deviseState.currentLightLevel.toString()
                        recommendedStr = hoursToPercent(settings.lightDurationHours).toInt().toString()
                    }
                    SensorType.TEMPERATURE -> {
                        currentValueStr = deviseState.currentTemperature.toString()
                        recommendedStr = settings.targetTemperature.toInt().toString()
                    }
                    SensorType.HUMIDITY -> {
                        currentValueStr = deviseState.currentHumidity.toString()
                        recommendedStr = settings.targetHumidity.toInt().toString()
                    }
                    SensorType.NUTRITION -> {
                        currentValueStr = deviseState.currentNutritionLevel.toString()
                        recommendedStr = mgToPercent(settings.nutritionTargetAmount).toInt().toString()
                    }
                }
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        currentValue = currentValueStr,
                        statRecommended = recommendedStr
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, currentValue = "--", statRecommended = "--")
                }
                e.printStackTrace()
            }
        }
    }

    private fun loadGraphData(period: StatisticPeriod) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val points: List<ChartPoint>
                val stats: Pair<String, String>

                when (period) {
                    StatisticPeriod.DAY -> {
                        var log = getTodayLogUseCase(deviceId)

                        if (log == null || log.hourlyReadings.isEmpty()) {
                            log = AnalyticsMocks.defaultDailyLog
                        }

                        points = mapHourlyLogToPoints(log)
                        stats = calculateStats(points)
                    }
                    StatisticPeriod.WEEK -> {
                        var logs = getWeekLogsUseCase(deviceId)

                        if (logs.isEmpty()) {
                            logs = generateMockLogs(7)
                        }

                        points = mapDailyLogsToPoints(logs, isWeek = true)
                        stats = calculateStats(points)
                    }
                    StatisticPeriod.MONTH -> {
                        var logs = getMonthLogsUseCase(deviceId)

                        if (logs.isEmpty()) {
                            logs = generateMockLogs(30)
                        }

                        points = mapDailyLogsToPoints(logs, isWeek = false)
                        stats = calculateStats(points)
                    }
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        chartData = points,
                        statPeriodValue = stats.first,
                        statTotalValue = stats.second
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun generateMockLogs(days: Int): List<DailyLog> {
        val today = LocalDate.now()
        return List(days) { index ->
            AnalyticsMocks.defaultDailyLog.copy(
                id = "mock_log_$index",
                date = today.minusDays((days - 1 - index).toLong()).toString()
            )
        }
    }

    private fun mapHourlyLogToPoints(log: DailyLog?): List<ChartPoint> {
        if (log == null || log.hourlyReadings.isEmpty()) return emptyList()

        return log.hourlyReadings.sortedBy { it.timestamp }.map { reading ->
            val value = extractValueFromHourly(reading)

            val time = Instant.ofEpochMilli(reading.timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
            val hourLabel = "${time.hour}:00"

            ChartPoint(
                value = value.toFloat(),
                labelTop = hourLabel,
                labelBottom = ""
            )
        }
    }

    private fun mapDailyLogsToPoints(logs: List<DailyLog>, isWeek: Boolean): List<ChartPoint> {
        if (logs.isEmpty()) return emptyList()

        return logs.map { log ->
            val avgValue = extractAvgValueFromDaily(log)
            val date = LocalDate.parse(log.date)

            ChartPoint(
                value = avgValue.toFloat(),
                labelTop = if (isWeek) date.format(DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH))
                else date.dayOfMonth.toString(),
                labelBottom = date.dayOfMonth.toString()
            )
        }
    }

    private fun extractValueFromHourly(reading: HourlyReading): Double {
        val type = sensorType ?: return 0.0
        return when (type) {
            SensorType.TEMPERATURE -> reading.temperature
            SensorType.HUMIDITY -> reading.humidity
            SensorType.LIGHT -> reading.light
            SensorType.NUTRITION -> reading.nutrition
        }
    }

    private fun extractAvgValueFromDaily(log: DailyLog): Double {
        val type = sensorType ?: return 0.0
        return when (type) {
            SensorType.TEMPERATURE -> (log.minTemp + log.maxTemp) / 2
            SensorType.HUMIDITY -> (log.minHumidity + log.maxHumidity) / 2
            SensorType.LIGHT -> log.totalPowerKwh / 24
            SensorType.NUTRITION -> log.totalNutritionMg / 24
        }
    }

    private fun calculateStats(points: List<ChartPoint>): Pair<String, String> {
        if (points.isEmpty()) return Pair("--", "--")

        val avg = points.map { it.value }.average()
        val max = points.maxOf { it.value }

        val avgStr = String.format(Locale.US, "%.1f", avg)
        val maxStr = String.format(Locale.US, "%.1f", max)

        return Pair(avgStr, maxStr)
    }

    fun hoursToPercent(hours: Double, maxHours: Int = 16): Double {
        if (hours == 0.0) return 0.0
        return ((hours.toFloat() / maxHours) * 100).toDouble()
    }

    private fun mgToPercent(mg: Double, maxMg: Int = 500): Double {
        if (mg == 0.0) return 0.0
        return ((mg.toFloat() / maxMg) * 100).toDouble().coerceIn(0.0, 100.0)
    }
}