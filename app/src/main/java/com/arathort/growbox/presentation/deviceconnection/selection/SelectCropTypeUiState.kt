package com.arathort.growbox.presentation.deviceconnection.selection

import com.arathort.growbox.domain.models.library.CropType

data class SelectCropTypeUiState(
    val cropTypes: List<CropType> = listOf(),
    val selectedCrop: CropType? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)