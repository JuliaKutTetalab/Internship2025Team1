package com.arathort.growbox.domain.models.library

data class CropType(
    val id: String,
    val name: String,
    val imageUrl: String,
    val description: String,

    val totalCycleDays: Int,
    val defaultTemperature: Double,
    val defaultHumidity: Double,
    val defaultLightHours: Double,
    val defaultNutritionAmount: Double
)