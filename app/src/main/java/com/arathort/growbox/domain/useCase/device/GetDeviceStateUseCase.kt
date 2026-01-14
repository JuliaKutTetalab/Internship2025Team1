package com.arathort.growbox.domain.useCase.device

import com.arathort.growbox.domain.models.device.DeviceState
import com.arathort.growbox.domain.repository.DeviceRepository
import javax.inject.Inject


class GetDeviceStateUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: String): DeviceState? {
        return deviceRepository.getDeviceState(deviceId)
    }
}