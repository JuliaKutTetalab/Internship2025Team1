package com.arathort.growbox.domain.useCase.device

import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.repository.DeviceRepository
import javax.inject.Inject

class UpdateDeviceSettingsUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceSettings: DeviceSettings){
        deviceRepository.saveDeviceSettings(deviceSettings)
    }
}