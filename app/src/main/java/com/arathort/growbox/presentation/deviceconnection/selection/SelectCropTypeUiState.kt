package com.arathort.growbox.presentation.deviceconnection.selection

data class SelectCropTypeUiState(
    val cropTypes: List<String> = listOf(
        "Microgreens",
        "Herbs",
        "Mushrooms",
        "Vegetables",
        "Flowering Plants"
    ),
    val selectedCrop: String = "Herbs"
)