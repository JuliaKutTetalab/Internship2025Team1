package com.arathort.growbox.presentation.settings

import com.arathort.growbox.domain.models.device.DeviceSettings

data class SettingsScreenUiState(
    val deviceSettings: DeviceSettings = DeviceSettings(
        deviceId = "",
        isLightAutomationEnabled = true,
        isVentAutomationEnabled = true,
        ventDurationHours = 5.0,
        lightDurationHours = 5.0,
        targetHumidity = 50.0,
        targetTemperature = 20.0,
        nutritionTargetAmount = 3.0,
        wateringTargetAmount = 50.0,
        wateringFrequencyIndex = 1,
        nutritionFrequencyIndex = 1
    )
)
