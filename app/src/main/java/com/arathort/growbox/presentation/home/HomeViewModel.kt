package com.arathort.growbox.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.onSensorDetailClick -> navigateToDetail(event.sensorType)
            is HomeUiEvent.onVentToggle -> toggleVent(event.isEnabled)
            is HomeUiEvent.onWateringToggle -> toggleWatering(event.isEnabled)
            HomeUiEvent.OnRefresh -> loadFakeData()
        }
    }

    init {
        onEvent(HomeUiEvent.OnRefresh)
    }

    private fun navigateToDetail(sensorType: SensorType) {
        TODO("Not yet implemented")
    }

    private fun toggleVent(isEnabled: Boolean) {
        _uiState.update { it.copy(isVentOn = isEnabled) }
    }

    private fun toggleWatering(isEnabled: Boolean) {
        _uiState.update { it.copy(isWateringOn = isEnabled) }
    }


    private fun loadFakeData() {
        val currentDays = 12
        val maxDays = 21
        val calculatedProgress = (currentDays.toFloat() / maxDays.toFloat()).coerceIn(0f, 1f)
        _uiState.update {
            it.copy(
                isLoading = false,
                cropName = "Microgreens",
                daysGrown = 12,
                totalDays = 21,
                temperature = 24,
                humidity = 58,
                lightLevel = 60,
                nutritionLevel = 78,
                isVentOn = true,
                isWateringOn = true,
                progress = calculatedProgress,
                connectionStatus = ConnectionStatus.CONNECTED
            )
        }

    }
}
