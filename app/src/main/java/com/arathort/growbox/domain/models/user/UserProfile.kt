package com.arathort.growbox.domain.models.user

import com.arathort.growbox.data.remote.dto.user.HarvestHistoryDto

data class UserProfile(
    val uid: String,
    val email: String,
    val displayName: String,


    val totalHarvestsCount: Int,
    val totalDaysActive: Int
)