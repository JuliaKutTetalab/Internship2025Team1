package com.arathort.growbox.data.remote.dto.library

import com.arathort.growbox.domain.models.library.CropType

data class CropTypeDto(

    val id: String = "",

    val name: String = "",

    val image_url: String = "",

    val description: String = "",

    val total_cycle_days: Int = 0,

    val default_temp: Double = 0.0,

    val default_humidity: Double = 0.0,

    val default_light_hours: Double = 0.0,

    val default_nutrition_amount: Double = 0.0

)
fun CropTypeDto.toDomain(): CropType {
    return CropType(
        id = id,
        name = name,
        imageUrl = image_url,
        description = description,
        totalCycleDays = total_cycle_days,
        defaultTemperature = default_temp,
        defaultHumidity = default_humidity,
        defaultLightHours = default_light_hours,
        defaultNutritionAmount = default_nutrition_amount
    )
}