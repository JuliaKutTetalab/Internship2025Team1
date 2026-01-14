package com.arathort.growbox.data.remote.dto.device

import com.arathort.growbox.domain.models.device.DeviceState

data class DeviceStateDto(
    val device_id: String,

    val owner_id: String,

    val active_crop_type_id: String?,

    val active_crop_name: String,
    val active_crop_image_url: String?,

    val start_date_timestamp: Long?,
    val estimated_harvest_days: Int?,
    val last_updated: Long,

    val current_temperature: Double,
    val current_humidity: Double,
    val current_light_level: Double,
    val current_nutrition_level: Double,

    val is_vent_running: Boolean,
    val is_watering_running: Boolean
)

fun DeviceStateDto.toDomain(): DeviceState {
    return DeviceState(
        deviceId = device_id,
        ownerId = owner_id,
        activeCropTypeId = active_crop_type_id,
        activeCropName = active_crop_name,
        activeCropImageUrl = active_crop_image_url,
        startDateTimestamp = start_date_timestamp,
        estimatedHarvestDays = estimated_harvest_days,
        lastUpdated = last_updated,
        currentTemperature = current_temperature,
        currentHumidity = current_humidity,
        currentLightLevel = current_light_level,
        currentNutritionLevel = current_nutrition_level,
        isVentRunning = is_vent_running,
        isWateringRunning = is_watering_running
    )
}

fun DeviceState.toDto(): DeviceStateDto {
    return DeviceStateDto(
        device_id = deviceId,
        owner_id = ownerId,
        active_crop_type_id = activeCropTypeId,
        active_crop_name = activeCropName,
        active_crop_image_url = activeCropImageUrl,
        start_date_timestamp = startDateTimestamp,
        estimated_harvest_days = estimatedHarvestDays,
        last_updated = lastUpdated,
        current_temperature = currentTemperature,
        current_humidity = currentHumidity,
        current_light_level = currentLightLevel,
        current_nutrition_level = currentNutritionLevel,
        is_vent_running = isVentRunning,
        is_watering_running = isWateringRunning
    )
}