package com.arathort.growbox.presentation.changeCrop

import com.arathort.growbox.domain.models.library.CropType

data class ChangeCropScreenUiState(
    val cropType: CropType? = null,
    val isLoading: Boolean = false,
    val daysFromPlant: Int = 0
)
