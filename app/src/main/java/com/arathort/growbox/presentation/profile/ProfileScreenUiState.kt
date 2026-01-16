package com.arathort.growbox.presentation.profile

import com.arathort.growbox.domain.models.device.DeviceState
import com.arathort.growbox.domain.models.user.UserProfile

data class ProfileScreenUiState(
    val userProfile: UserProfile = UserProfile(
        uid = "",
        email = "nick.name@mail.com",
        displayName = "name",
        totalHarvestsCount = 5,
        totalDaysActive = 84
    ),
    val deviceState: DeviceState = DeviceState(
        deviceId = "",
        ownerId = "",
        activeCropTypeId = "",
        activeCropName = "Microgreens",
        activeCropImageUrl = "",
        startDateTimestamp = 555555L,
        estimatedHarvestDays = 45,
        lastUpdated = 3245634L,
        currentTemperature = 45.0,
        currentHumidity = 45.0,
        currentLightLevel = 456.0,
        currentNutritionLevel = 345.0,
        isVentRunning = false,
        isWateringRunning = false
    )
)
