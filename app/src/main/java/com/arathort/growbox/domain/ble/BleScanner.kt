package com.arathort.growbox.domain.ble

import com.arathort.growbox.domain.models.device.ScannedDevice
import kotlinx.coroutines.flow.Flow

interface BleScanner {
    fun scan(): Flow<List<ScannedDevice>>
}