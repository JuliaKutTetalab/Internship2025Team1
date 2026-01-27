package com.arathort.growbox.domain.useCase.device

import com.arathort.growbox.domain.repository.DeviceRepository
import jakarta.inject.Inject

class ControlDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(
        turnVentOn: Boolean? = null,
        turnWateringOn: Boolean? = null
    ) {
        deviceRepository.sendDeviceCommand(turnVentOn, turnWateringOn)
    }
}