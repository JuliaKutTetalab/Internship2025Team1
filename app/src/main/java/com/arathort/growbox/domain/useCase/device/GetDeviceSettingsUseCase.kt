package com.arathort.growbox.domain.useCase.device

import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.repository.DeviceRepository
import javax.inject.Inject

class GetDeviceSettingsUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: String): DeviceSettings? {
        return deviceRepository.getDeviceSettings(deviceId)
    }
}