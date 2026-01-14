package com.arathort.growbox.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.device.ControlDeviceUseCase
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
    private val controlDeviceUseCase: ControlDeviceUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effect = Channel<HomeUiEffect>()
    val effect = _effect.receiveAsFlow()

    private val deviceId = "1"

    init {
        collectData()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.onSensorDetailClick -> navigateToDetail(event.sensorType)
            is HomeUiEvent.onVentToggle -> toggleVent(event.isEnabled)
            is HomeUiEvent.onWateringToggle -> toggleWatering(event.isEnabled)
        }
    }

    private fun collectData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val deviceState = getDeviceStateUseCase(deviceId) ?: defaultState

                if (deviceState != null) {
                    val now = System.currentTimeMillis()
                    val startDate = deviceState.startDateTimestamp ?: now
                    val diffInMillis = now - startDate
                    val calculatedDaysGrown =
                        (diffInMillis / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(0)
                    val totalDays = deviceState.estimatedHarvestDays ?: 1

                    val calculatedProgress =
                        (calculatedDaysGrown.toFloat() / totalDays.toFloat()).coerceIn(0f, 1f)

                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,

                            cropName = deviceState.activeCropName ,
                            daysGrown = calculatedDaysGrown,
                            totalDays = deviceState.estimatedHarvestDays ?: 0,
                            progress = calculatedProgress,

                            temperature = deviceState.currentTemperature.toInt(),
                            humidity = deviceState.currentHumidity.toInt(),
                            lightLevel = deviceState.currentLightLevel.toInt(),
                            nutritionLevel = deviceState.currentNutritionLevel.toInt(),

                            isVentOn = deviceState.isVentRunning,
                            isWateringOn = deviceState.isWateringRunning
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    _effect.send(HomeUiEffect.ShowToast)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _effect.send(HomeUiEffect.ShowToast)
            }
        }
    }

    private fun toggleVent(isEnabled: Boolean) {
        viewModelScope.launch {
            val oldState = _uiState.value.isVentOn
            _uiState.update { it.copy(isVentOn = isEnabled) }

            try {
                controlDeviceUseCase(
                    deviceId = deviceId,
                    turnVentOn = isEnabled
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(isVentOn = oldState) }
                _effect.send(HomeUiEffect.ShowToast)
            }
        }
    }

    private fun toggleWatering(isEnabled: Boolean) {
        viewModelScope.launch {
            val oldState = _uiState.value.isWateringOn
            _uiState.update { it.copy(isWateringOn = isEnabled) }

            try {
                controlDeviceUseCase(
                    deviceId = deviceId,
                    turnWateringOn = isEnabled
                )
            } catch (e: Exception) {
                _uiState.update { it.copy(isWateringOn = oldState) }
                _effect.send(HomeUiEffect.ShowToast)
            }
        }
    }

    private fun navigateToDetail(sensorType: SensorType) {
        viewModelScope.launch {
            try {
                _effect.send(HomeUiEffect.NavigateToDetail(sensorType))
            } catch (e: Exception) {
                _effect.send(HomeUiEffect.ShowToast)
            }
        }
    }
}