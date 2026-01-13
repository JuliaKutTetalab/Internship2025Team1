package com.arathort.growbox.data.remote.dto.device

import com.arathort.growbox.domain.models.device.DeviceSettings

data class DeviceSettingsDto(
    val device_id: String = "",

    val is_vent_automation_enabled: Boolean = false,
    val vent_duration_hours: Double = 0.0,

    val is_light_automation_enabled: Boolean = false,
    val light_duration_hours: Double = 0.0,

    val target_temperature: Double = 0.0,
    val target_humidity: Double = 0.0,

    val nutrition_target_amount: Double = 0.0,
    val nutrition_frequency_index: Int = 0,

    val watering_target_amount: Double = 0.0,
    val watering_frequency_index: Int = 0
)

fun DeviceSettingsDto.toDomain(): DeviceSettings {
    return DeviceSettings(
        deviceId = device_id,
        isVentAutomationEnabled = is_vent_automation_enabled,
        ventDurationHours = vent_duration_hours,
        isLightAutomationEnabled = is_light_automation_enabled,
        lightDurationHours = light_duration_hours,
        targetTemperature = target_temperature,
        targetHumidity = target_humidity,
        nutritionTargetAmount = nutrition_target_amount,
        nutritionFrequencyIndex = nutrition_frequency_index,
        wateringTargetAmount = watering_target_amount,
        wateringFrequencyIndex = watering_frequency_index
    )
}

fun DeviceSettings.toDto(): DeviceSettingsDto {
    return DeviceSettingsDto(
        device_id = deviceId,
        is_vent_automation_enabled = isVentAutomationEnabled,
        vent_duration_hours = ventDurationHours,
        is_light_automation_enabled = isLightAutomationEnabled,
        light_duration_hours = lightDurationHours,
        target_temperature = targetTemperature,
        target_humidity = targetHumidity,
        nutrition_target_amount = nutritionTargetAmount,
        nutrition_frequency_index = nutritionFrequencyIndex,
        watering_target_amount = wateringTargetAmount,
        watering_frequency_index = wateringFrequencyIndex
    )
}