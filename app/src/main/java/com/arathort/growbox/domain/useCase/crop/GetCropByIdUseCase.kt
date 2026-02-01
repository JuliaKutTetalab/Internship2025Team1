package com.arathort.growbox.domain.useCase.crop

import com.arathort.growbox.domain.models.library.CropType
import com.arathort.growbox.domain.repository.LibraryRepository
import javax.inject.Inject

class GetCropByIdUseCase @Inject constructor(
    private val libraryRepository: LibraryRepository
) {
    suspend operator fun invoke(id: String): CropType? {
        return libraryRepository.getCropById(id)
    }
}