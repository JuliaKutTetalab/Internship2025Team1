package com.arathort.growbox.data.remote.dto.user

import com.arathort.growbox.domain.models.user.UserProfile

data class UserProfileDto(
    val uid: String,

    val email: String,
    val display_name: String,

    val total_harvests_count: Int,
    val total_days_active: Int
)
fun UserProfileDto.toDomain(): UserProfile {
    return UserProfile(
        uid = uid,
        email = email,
        displayName = display_name,
        totalHarvestsCount = total_harvests_count,
        totalDaysActive = total_days_active
    )
}

fun UserProfile.toDto(): UserProfileDto {
    return UserProfileDto(
        uid = uid,
        email = email,
        display_name = displayName,
        total_harvests_count = totalHarvestsCount,
        total_days_active = totalDaysActive
    )
}