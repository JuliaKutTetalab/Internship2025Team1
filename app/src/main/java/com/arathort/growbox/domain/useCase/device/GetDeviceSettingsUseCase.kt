package com.arathort.growbox.domain.useCase.device

import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.repository.DeviceRepository
import jakarta.inject.Inject

class GetDeviceSettingsUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: String): Result<DeviceSettings> {
        return try {
            val settings = repository.getDeviceSettings(deviceId)

            if (settings != null) {
                Result.success(settings)
            } else {
                Result.failure(IllegalStateException("Settings for device $deviceId not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}