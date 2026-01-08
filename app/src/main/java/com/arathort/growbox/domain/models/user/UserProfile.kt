package com.arathort.growbox.domain.models.user

import com.arathort.growbox.data.remote.dto.user.HarvestHistoryDto

data class UserProfile(

    val uid: String,

    val email: String,

    val displayName: String,

    val photoUrl: String?,

    val totalHarvestsCount: Int,

    val totalDaysActive: Int,

    val currentCropType: String

)

fun HarvestHistoryDto.toDomain(): HarvestHistoryItem {
    return HarvestHistoryItem(
        id = id,
        cropName = crop_name,
        cropImageUrl = crop_image_url,
        harvestDate = harvest_date,
        daysGrown = days_grown,
        yieldAmount = yield_amount,
        harvestCount = harvest_count
    )
}