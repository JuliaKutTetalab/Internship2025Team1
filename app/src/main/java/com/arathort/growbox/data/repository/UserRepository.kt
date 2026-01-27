package com.arathort.growbox.data.repository

import android.util.Log
import com.arathort.growbox.data.remote.dto.user.HarvestHistoryDto
import com.arathort.growbox.data.remote.dto.user.UserProfileDto
import com.arathort.growbox.data.remote.dto.user.toDomain
import com.arathort.growbox.data.remote.dto.user.toDto
import com.arathort.growbox.data.repository.common.Collections
import com.arathort.growbox.domain.models.user.HarvestHistoryItem
import com.arathort.growbox.domain.models.user.UserProfile
import com.arathort.growbox.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override suspend fun getUserProfile(): UserProfile? {
        return try {
            val userId = firebaseAuth.currentUser?.uid ?: return null
            firestore.collection(Collections.users)
                .document(userId)
                .get()
                .await()
                .toObject(UserProfileDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            Log.e("My tag", e.message.toString())
            null
        }
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        val dto = profile.toDto()
        firestore.collection(Collections.users)
            .document(profile.uid)
            .set(dto)
            .await()
    }

    override suspend fun getHarvestHistory(userId: String): List<HarvestHistoryItem> {
        return try {
            firestore.collection(Collections.history)
                .whereEqualTo(Collections.userId, userId)
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
            firestore.collection(Collections.history).document()
        } else {
            firestore.collection(Collections.history).document(item.id)
        }
        val finalDto = dto.copy(id = docRef.id)
        docRef.set(finalDto).await()
    }
}