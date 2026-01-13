package com.arathort.growbox.domain.models.device

data class DeviceState(
    val deviceId: String,
    val ownerId: String,

    val activeCropTypeId: String?,
    val activeCropName: String?,
    val activeCropImageUrl: String?,

    val startDateTimestamp: Long,
    val estimatedHarvestDays: Int,
    val lastUpdated: Long,

    val currentTemperature: Double,
    val currentHumidity: Double,
    val currentLightLevel: Double,
    val currentNutritionLevel: Double,

    val isVentRunning: Boolean,
    val isWateringRunning: Boolean
)