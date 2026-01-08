package com.arathort.growbox.domain.models.device

data class DeviceSettings(

    val isVentAutomationEnabled: Boolean,

    val ventDurationHours: Double,

    val isLightAutomationEnabled: Boolean,

    val lightDurationHours: Double,

    val targetTemperature: Double,

    val targetHumidity: Double,

    val nutritionTargetAmount: Double,

    val nutritionFrequencyIndex: Int,

    val wateringTargetAmount: Double,

    val wateringFrequencyIndex: Int
)