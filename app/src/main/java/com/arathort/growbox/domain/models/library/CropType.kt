package com.arathort.growbox.domain.models.library

data class CropType(
    val id: String,
    val name: String,
    val imageUrl: String,
    val totalCycleDays: Int,
)