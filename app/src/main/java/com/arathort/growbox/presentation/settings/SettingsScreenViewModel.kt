package com.arathort.growbox.presentation.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsScreenUiState())
    val uiState: StateFlow<SettingsScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: SettingsScreenUiEvent){
        when(event){
            is SettingsScreenUiEvent.OnHumidityChange -> TODO()
            is SettingsScreenUiEvent.OnLighteningChange -> TODO()
            is SettingsScreenUiEvent.OnNutritionChange -> TODO()
            is SettingsScreenUiEvent.OnNutritionFrequencyChange -> TODO()
            is SettingsScreenUiEvent.OnTemperatureChange -> TODO()
            is SettingsScreenUiEvent.OnVentChange -> TODO()
            is SettingsScreenUiEvent.OnWateringChange -> TODO()
            is SettingsScreenUiEvent.OnWateringFrequencyChange -> TODO()
            is SettingsScreenUiEvent.TurnLightening -> TODO()
            is SettingsScreenUiEvent.TurnVent -> TODO()
        }
    }
}