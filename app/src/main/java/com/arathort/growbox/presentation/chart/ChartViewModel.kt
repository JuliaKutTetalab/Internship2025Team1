package com.arathort.growbox.presentation.chart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arathort.growbox.presentation.home.SensorType
import com.arathort.growbox.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle

) : ViewModel() {
    private val _uiState = MutableStateFlow(ChartUiState(isLoading = true))
    private val uiState = _uiState.asStateFlow()
    val typeString = savedStateHandle.get<String>("sensorType")
    val sensorType = SensorType.valueOf(typeString ?: "TEMPERATURE")

    init {
        showInfo(sensorType)
    }

    fun onEvent(event: ChartUiEvent) {
        when (event) {
            is ChartUiEvent.OnDailyGraphicSelected ->TODO()
            is ChartUiEvent.OnMonthlyGraphicSelected ->TODO()
            is ChartUiEvent.OnReturnButtonClick ->TODO()
            is ChartUiEvent.OnWeekGraphicSelected ->TODO()
        }
    }

    private fun showInfo(sensorType: SensorType) {
        viewModelScope.launch {

        }
        _uiState.update {
            it.copy(
                screenTitle = sensorType.title,
                sensorIcon = sensorType.icon,
                unit = sensorType.unit,
                unitCalculate = sensorType.unitCalculate,
            )
        }
    }

    private fun loadData(period: StatisticPeriod) {

    }
}