package com.arathort.growbox.data.remote.dto.device

import com.arathort.growbox.domain.models.device.DeviceState

data class DeviceStateDto(
    val device_id: String = "",

    val active_crop_name: String = "",

    val active_crop_image_url: String = "",

    val start_date_timestamp: Long = 0,

    val estimated_harvest_days: Int = 0,

    val current_temperature: Double = 0.0,

    val current_humidity: Double = 0.0,

    val current_light_level: Double = 0.0,

    val current_nutrition_level: Double = 0.0,

    val is_vent_running: Boolean = false,

    val is_watering_running: Boolean = false,

    val last_updated: Long = 0

)
fun DeviceStateDto.toDomain(): DeviceState {
    return DeviceState(
        deviceId = device_id,
        activeCropName = active_crop_name,
        activeCropImageUrl = active_crop_image_url,
        startDateTimestamp = start_date_timestamp,
        estimatedHarvestDays = estimated_harvest_days,
        currentTemperature = current_temperature,
        currentHumidity = current_humidity,
        currentLightLevel = current_light_level,
        currentNutritionLevel = current_nutrition_level,
        isVentRunning = is_vent_running,
        isWateringRunning = is_watering_running,
        lastUpdated = last_updated
    )
}