package com.arathort.growbox.data.repository

import com.arathort.growbox.data.remote.dto.library.CropTypeDto
import com.arathort.growbox.data.remote.dto.library.toDomain
import com.arathort.growbox.domain.models.library.CropType
import com.arathort.growbox.domain.repository.LibraryRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : LibraryRepository {

    override suspend fun getAllCrops(): List<CropType> {
        return try {
            firestore.collection("library_crops")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(CropTypeDto::class.java)?.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCropById(cropId: String): CropType? {
        return try {
            firestore.collection("library_crops")
                .document(cropId)
                .get()
                .await()
                .toObject(CropTypeDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}