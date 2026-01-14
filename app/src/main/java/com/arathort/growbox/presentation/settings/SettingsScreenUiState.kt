package com.arathort.growbox.presentation.settings

import com.arathort.growbox.domain.models.device.DeviceSettings

data class SettingsScreenUiState(
    val deviceSettings: DeviceSettings = defaultSettings,
    val isLoading: Boolean = false
)

val defaultSettings = DeviceSettings(
    deviceId = "1",
    isLightAutomationEnabled = true,
    isVentAutomationEnabled = true,
    ventDurationHours = 12.0,
    lightDurationHours = 8.0,
    targetHumidity = 50.0,
    targetTemperature = 24.0,
    nutritionTargetAmount = 250.0,
    wateringTargetAmount = 250.0,
    wateringFrequencyIndex = 0,
    nutritionFrequencyIndex = 0
)
