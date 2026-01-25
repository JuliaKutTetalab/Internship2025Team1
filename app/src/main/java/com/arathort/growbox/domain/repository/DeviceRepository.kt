package com.arathort.growbox.domain.repository

import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.models.device.DeviceState
import com.arathort.growbox.domain.models.library.CropType

interface DeviceRepository {
    suspend fun getDeviceState(): Result<DeviceState?>

    suspend fun getUserDevices(userId: String): List<DeviceState>

    suspend fun getDeviceSettings(): DeviceSettings?

    suspend fun saveDeviceSettings(settings: DeviceSettings)
    suspend fun sendDeviceCommand(
        isVentEnabled: Boolean? = null,
        isWateringEnabled: Boolean? = null
    )

    suspend fun saveDevice(cropType: CropType): Result<Unit>

}

