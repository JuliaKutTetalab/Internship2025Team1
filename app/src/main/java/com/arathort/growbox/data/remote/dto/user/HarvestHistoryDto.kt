package com.arathort.growbox.data.remote.dto.user

import com.arathort.growbox.domain.models.user.HarvestHistoryItem

data class HarvestHistoryDto(
    val id: String,

    val user_id: String,

    val crop_type_id: String,

    val crop_name: String,
    val crop_image_url: String,

    val harvest_date: Long,
    val days_grown: Int,
    val yield_amount: Double,
    val harvest_count: Int
)

fun HarvestHistoryDto.toDomain(): HarvestHistoryItem {
    return HarvestHistoryItem(
        id = id,
        userId = user_id,
        cropTypeId = crop_type_id,
        cropName = crop_name,
        cropImageUrl = crop_image_url,
        harvestDate = harvest_date,
        daysGrown = days_grown,
        yieldAmount = yield_amount,
        harvestCount = harvest_count
    )
}

fun HarvestHistoryItem.toDto(): HarvestHistoryDto {
    return HarvestHistoryDto(
        id = id,
        user_id = userId,
        crop_type_id = cropTypeId,
        crop_name = cropName,
        crop_image_url = cropImageUrl,
        harvest_date = harvestDate,
        days_grown = daysGrown,
        yield_amount = yieldAmount,
        harvest_count = harvestCount
    )
}