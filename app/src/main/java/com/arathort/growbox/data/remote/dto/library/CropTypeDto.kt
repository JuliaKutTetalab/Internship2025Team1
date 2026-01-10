package com.arathort.growbox.data.remote.dto.library

import com.arathort.growbox.domain.models.library.CropType

data class CropTypeDto(
    val id: String,

    val name: String,
    val image_url: String,
    val description: String,

    val total_cycle_days: Int,
    val default_temperature: Double,
    val default_humidity: Double,
    val default_light_hours: Double,
    val default_nutrition_amount: Double
)

fun CropTypeDto.toDomain(): CropType {
    return CropType(
        id = id,
        name = name,
        imageUrl = image_url,
        description = description,
        totalCycleDays = total_cycle_days,
        defaultTemperature = default_temperature,
        defaultHumidity = default_humidity,
        defaultLightHours = default_light_hours,
        defaultNutritionAmount = default_nutrition_amount
    )
}

fun CropType.toDto(): CropTypeDto {
    return CropTypeDto(
        id = id,
        name = name,
        image_url = imageUrl,
        description = description,
        total_cycle_days = totalCycleDays,
        default_temperature = defaultTemperature,
        default_humidity = defaultHumidity,
        default_light_hours = defaultLightHours,
        default_nutrition_amount = defaultNutritionAmount
    )
}