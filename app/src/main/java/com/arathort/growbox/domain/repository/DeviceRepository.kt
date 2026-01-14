package com.arathort.growbox.domain.repository

import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.models.device.DeviceState

interface DeviceRepository {
    suspend fun getDeviceState(deviceId: String): DeviceState?

    suspend fun getUserDevices(userId: String): List<DeviceState>

    suspend fun getDeviceSettings(deviceId: String): DeviceSettings?

    suspend fun saveDeviceSettings(settings: DeviceSettings)
    suspend fun sendDeviceCommand(
        deviceId: String,
        isVentEnabled: Boolean? = null,
        isWateringEnabled: Boolean? = null
    )

}

