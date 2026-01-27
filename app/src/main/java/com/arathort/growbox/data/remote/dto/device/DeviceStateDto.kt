package com.arathort.growbox.data.remote.dto.device

import com.arathort.growbox.domain.models.device.DeviceState
import com.google.firebase.firestore.PropertyName

data class DeviceStateDto(
    val device_id: String = "",

    val owner_id: String = "",

    val active_crop_type_id: String? = "",

    val active_crop_name: String = "",
    val active_crop_image_url: String? = null,

    val start_date_timestamp: Long? = null,
    val estimated_harvest_days: Int? = null,
    val last_updated: Long = System.currentTimeMillis(),

    val current_temperature: Double = 0.0,
    val current_humidity: Double = 0.0,
    val current_light_level: Double = 0.0,
    val current_nutrition_level: Double = 0.0,

    @PropertyName("_vent_running")
    val is_vent_running: Boolean = false,

    @PropertyName("_watering_running")
    val is_watering_running: Boolean = false
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