package com.arathort.growbox.presentation.settings

sealed class SettingsScreenUiEvent {
    data class TurnVent(val ventilationState: Boolean) : SettingsScreenUiEvent()
    data class TurnLightening(val lighteningState: Boolean) : SettingsScreenUiEvent()
    data class OnVentChange(val value: Double) : SettingsScreenUiEvent()
    data class OnLighteningChange(val value: Double) : SettingsScreenUiEvent()
    data class OnTemperatureChange(val value: Double) : SettingsScreenUiEvent()
    data class OnHumidityChange(val value: Double) : SettingsScreenUiEvent()
    data class OnNutritionChange(val value: Double) : SettingsScreenUiEvent()
    data class OnNutritionFrequencyChange(val value: Int) : SettingsScreenUiEvent()
    data class OnWateringChange(val value: Double) : SettingsScreenUiEvent()
    data class OnWateringFrequencyChange(val value: Int) : SettingsScreenUiEvent()
}