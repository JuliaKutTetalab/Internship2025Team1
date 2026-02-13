package com.arathort.growbox.presentation.deviceconnection.search

import com.arathort.growbox.domain.models.device.ScannedDevice

data class SearchingScreenUiState(
    val scannedDevices: List<ScannedDevice> = emptyList(),
    val isScanning: Boolean = false,
    val error: String? = null
)
data class MockGrowBox(
    val name: String = "Growbox",
    val model: String = "Fantastic Gin-10",
    val version: String = "1.1.5",
)
