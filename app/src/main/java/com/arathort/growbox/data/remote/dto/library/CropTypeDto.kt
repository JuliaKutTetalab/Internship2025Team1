package com.arathort.growbox.data.remote.dto.library

import com.arathort.growbox.domain.models.library.CropType

data class CropTypeDto(
    val id: String="",
    val name: String="",
    val image_url: String="",
    val total_cycle_days: Int=0,
)

fun CropTypeDto.toDomain(): CropType {
    return CropType(
        id = id,
        name = name,
        imageUrl = image_url,
        totalCycleDays = total_cycle_days,
    )
}

fun CropType.toDto(): CropTypeDto {
    return CropTypeDto(
        id = id,
        name = name,
        image_url = imageUrl,
        total_cycle_days = totalCycleDays
    )
}