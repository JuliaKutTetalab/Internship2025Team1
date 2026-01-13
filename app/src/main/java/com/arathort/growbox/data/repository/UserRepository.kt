package com.arathort.growbox.data.repository

import com.arathort.growbox.data.remote.dto.user.HarvestHistoryDto
import com.arathort.growbox.data.remote.dto.user.UserProfileDto
import com.arathort.growbox.data.remote.dto.user.toDomain
import com.arathort.growbox.data.remote.dto.user.toDto
import com.arathort.growbox.domain.models.user.HarvestHistoryItem
import com.arathort.growbox.domain.models.user.UserProfile
import com.arathort.growbox.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            firestore.collection("users")
                .document(uid)
                .get()
                .await()
                .toObject(UserProfileDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        val dto = profile.toDto()
        firestore.collection("users")
            .document(profile.uid)
            .set(dto)
            .await()
    }

    override suspend fun getHarvestHistory(userId: String): List<HarvestHistoryItem> {
        return try {
            firestore.collection("harvest_history")
                .whereEqualTo("user_id", userId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(HarvestHistoryDto::class.java)?.toDomain() }
                .sortedByDescending { it.harvestDate }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addHarvestHistoryItem(item: HarvestHistoryItem) {
        val dto = item.toDto()
        val docRef = if (item.id.isEmpty()) {
            firestore.collection("harvest_history").document()
        } else {
            firestore.collection("harvest_history").document(item.id)
        }
        val finalDto = dto.copy(id = docRef.id)
        docRef.set(finalDto).await()
    }
}