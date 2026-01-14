package com.arathort.growbox.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.device.GetDeviceSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDeviceSettingsUseCase: GetDeviceSettingsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _effect = Channel<HomeUiEffect>()
    val effect = _effect.receiveAsFlow()

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


    private fun navigateToDetail(sensorType: SensorType) {
        viewModelScope.launch {
            _effect.send(HomeUiEffect.NavigateToDetail(sensorType))
        }
    }

    private fun toggleVent(isEnabled: Boolean) {
        viewModelScope.launch {
            getDeviceSettingsUseCase.invoke("1")
        }
    }

    private fun toggleWatering(isEnabled: Boolean) {
        _uiState.update { it.copy(isWateringOn = isEnabled) }
    }


    private fun collectData() {
        val currentDays = 12
        val maxDays = 21
        val calculatedProgress = (currentDays.toFloat() / maxDays.toFloat()).coerceIn(0f, 1f)

    }
}
