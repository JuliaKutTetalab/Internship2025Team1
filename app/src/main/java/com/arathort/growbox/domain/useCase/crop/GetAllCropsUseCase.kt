package com.arathort.growbox.domain.useCase.crop

import com.arathort.growbox.domain.models.library.CropType
import com.arathort.growbox.domain.repository.LibraryRepository
import javax.inject.Inject

class GetAllCropsUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    suspend operator fun invoke(): List<CropType> {
        return libraryRepository.getAllCrops()
    }
}