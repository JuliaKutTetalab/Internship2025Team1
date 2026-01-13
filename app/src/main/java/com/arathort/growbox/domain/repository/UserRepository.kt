package com.arathort.growbox.domain.repository

import com.arathort.growbox.domain.models.user.HarvestHistoryItem
import com.arathort.growbox.domain.models.user.UserProfile

interface UserRepository {
    suspend fun getUserProfile(uid: String): UserProfile?

    suspend fun saveUserProfile(profile: UserProfile)

    suspend fun getHarvestHistory(userId: String): List<HarvestHistoryItem>

    suspend fun addHarvestHistoryItem(item: HarvestHistoryItem)
}