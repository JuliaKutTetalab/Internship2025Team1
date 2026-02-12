package com.arathort.growbox.domain.models.device

data class ScannedDevice(
    val name: String,
    val address: String,
    val rssi: Int
)
