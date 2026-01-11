package com.arathort.growbox.domain.repository

import com.arathort.growbox.domain.models.library.CropType

interface LibraryRepository {
    suspend fun getAllCrops(): List<CropType>

    suspend fun getCropById(cropId: String): CropType?
}