package com.arathort.growbox.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.device.GetDeviceSettingsUseCase
import com.arathort.growbox.domain.useCase.device.UpdateDeviceSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val updateDeviceSettingsUseCase: UpdateDeviceSettingsUseCase,
    private val getDeviceSettingsUseCase: GetDeviceSettingsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsScreenUiState())
    val uiState: StateFlow<SettingsScreenUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(deviceSettings = getDeviceSettingsUseCase("1") ?: defaultSettings)
            }
        }
    }

    fun onEvent(event: SettingsScreenUiEvent) {
        _uiState.update { state ->
            val currentSettings = state.deviceSettings

            val newSettings = when (event) {
                is SettingsScreenUiEvent.OnHumidityChange -> currentSettings.copy(targetHumidity = event.value)
                is SettingsScreenUiEvent.OnLighteningChange -> currentSettings.copy(
                    lightDurationHours = event.value
                )

                is SettingsScreenUiEvent.OnNutritionChange -> currentSettings.copy(
                    nutritionTargetAmount = event.value
                )

                is SettingsScreenUiEvent.OnNutritionFrequencyChange -> currentSettings.copy(
                    nutritionFrequencyIndex = event.value
                )

                is SettingsScreenUiEvent.OnTemperatureChange -> currentSettings.copy(
                    targetTemperature = event.value
                )

                is SettingsScreenUiEvent.OnVentChange -> currentSettings.copy(ventDurationHours = event.value)
                is SettingsScreenUiEvent.OnWateringChange -> currentSettings.copy(
                    wateringTargetAmount = event.value
                )

                is SettingsScreenUiEvent.OnWateringFrequencyChange -> currentSettings.copy(
                    wateringFrequencyIndex = event.value
                )

                is SettingsScreenUiEvent.TurnLightening -> currentSettings.copy(
                    isLightAutomationEnabled = event.lighteningState
                )

                is SettingsScreenUiEvent.TurnVent -> currentSettings.copy(isVentAutomationEnabled = event.ventilationState)
            }

            state.copy(deviceSettings = newSettings)
        }
        viewModelScope.launch {
            updateDeviceSettingsUseCase(_uiState.value.deviceSettings)
        }
    }
}