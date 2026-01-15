package com.arathort.growbox.presentation.home

import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.models.device.DeviceState
import com.arathort.growbox.domain.models.library.CropType

data class HomeUiState(
    val isLoading: Boolean=false,
    val cropName:String="",
    val daysGrown:Int=0,
    val totalDays:Int=0,
    val temperature: Int=0,
    val humidity:Int=0,
    val lightLevel:Int=0,
    val nutritionLevel:Int=0,
    val isVentOn: Boolean=false,
    val isWateringOn:Boolean=false,
    val progress:Float=0f,
){
    val daysRemaining: Int
        get() = (totalDays - daysGrown).coerceAtLeast(0)
}

private val daysAgo5 = System.currentTimeMillis() - (5L * 24 * 60 * 60 * 1000)

val defaultState = DeviceState(
    deviceId = "1",
    ownerId = "user_01",

    activeCropTypeId = "microgreens_basil",
    activeCropName = "Microgreens Basil",
    activeCropImageUrl = "",

    startDateTimestamp = daysAgo5,
    estimatedHarvestDays = 14,

    lastUpdated = System.currentTimeMillis(),

    currentTemperature = 24.5,
    currentHumidity = 60.0,
    currentLightLevel = 40.0,
    currentNutritionLevel = 65.0,

    isVentRunning = true,
    isWateringRunning = false
)