package com.arathort.growbox.presentation.home

import kotlinx.serialization.Serializable

sealed interface HomeUiEvent {
    data class onVentToggle(val isEnabled: Boolean) : HomeUiEvent
    data class onWateringToggle(val isEnabled: Boolean) : HomeUiEvent
    data class onSensorDetailClick(val sensorType: SensorType) : HomeUiEvent
}
@Serializable
enum class SensorType() {
    LIGHT, TEMPERATURE, HUMIDITY, NUTRITION
}
