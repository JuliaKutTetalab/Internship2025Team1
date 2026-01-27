package com.arathort.growbox.presentation.harvest

import com.arathort.growbox.domain.models.library.CropType

data class MyHarvestScreenUiState(
    val crops: List<CropType> = emptyList(),
    val isLoading: Boolean = false
)
