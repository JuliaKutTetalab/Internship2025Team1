package com.arathort.growbox.domain.useCase.device

import com.arathort.growbox.domain.models.library.CropType
import com.arathort.growbox.domain.repository.DeviceRepository
import javax.inject.Inject

class SaveDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(cropType: CropType): Result<Unit> {
        return deviceRepository.saveDevice(cropType)
    }
}