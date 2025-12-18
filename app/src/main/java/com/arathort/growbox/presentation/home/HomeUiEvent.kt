package com.arathort.growbox.presentation.home

sealed interface HomeUiEvent {
    data class onVentToggle(val isEnabled: Boolean) : HomeUiEvent
    data class onWateringToggle(val isEnabled: Boolean) : HomeUiEvent
    data class onSensorDetailClick(val sensorType: SensorType) : HomeUiEvent
    data object OnRefresh : HomeUiEvent
}

enum class SensorType() {
    LIGHT, TEMPERATURE, HUMIDITY, NUTRITION
}
