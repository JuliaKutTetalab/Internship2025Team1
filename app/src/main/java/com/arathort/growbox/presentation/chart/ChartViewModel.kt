package com.arathort.growbox.presentation.chart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arathort.growbox.domain.useCase.device.GetDeviceSettingsUseCase
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import com.arathort.growbox.presentation.home.SensorType
import com.arathort.growbox.presentation.home.defaultState
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.presentation.settings.defaultSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDeviceSettingsUseCase: GetDeviceSettingsUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChartUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()
    val typeString = savedStateHandle.get<String>("sensorType")
    val sensorType = SensorType.valueOf(typeString ?: "TEMPERATURE")
    private val deviceId = "1"

    init {
        showInfo(sensorType)
        loadData(sensorType)
    }

    fun onEvent(event: ChartUiEvent) {
        when (event) {
            is ChartUiEvent.OnDailyGraphicSelected -> TODO()
            is ChartUiEvent.OnMonthlyGraphicSelected -> TODO()
            is ChartUiEvent.OnWeekGraphicSelected -> TODO()
            is ChartUiEvent.OnReturnButtonClick -> TODO()
        }
    }

    private fun showInfo(sensorType: SensorType) {
        _uiState.update {
            it.copy(
                screenTitle = sensorType.title,
                sensorIcon = sensorType.icon,
                unit = sensorType.unit,
                unitCalculate = sensorType.unitCalculate,
            )
        }
    }

    private fun loadData(sensorType: SensorType) {

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val settings = getDeviceSettingsUseCase(deviceId) ?: defaultSettings
                val deviseState = getDeviceStateUseCase(deviceId) ?: defaultState

                var currentValueStr = ""
                var recommendedStr = ""

                when (sensorType) {
                    SensorType.LIGHT -> {
                        currentValueStr = deviseState.currentLightLevel.toString()
                        recommendedStr = hoursToPercent(settings.lightDurationHours).toString()
                    }

                    SensorType.TEMPERATURE -> {
                        currentValueStr = deviseState.currentTemperature.toString()
                        recommendedStr = settings.targetTemperature.toString()
                    }

                    SensorType.HUMIDITY -> {
                        currentValueStr = deviseState.currentHumidity.toString()
                        recommendedStr = settings.targetHumidity.toString()
                    }

                    SensorType.NUTRITION -> {
                        currentValueStr = deviseState.currentNutritionLevel.toString()
                        recommendedStr = mgToPercent(settings.nutritionTargetAmount).toString()
                    }
                }
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        currentValue = "$currentValueStr ${sensorType.unit}",
                        statRecommended = "$recommendedStr ${sensorType.unit}"
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false)
                }
                e.printStackTrace()
            }
        }
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