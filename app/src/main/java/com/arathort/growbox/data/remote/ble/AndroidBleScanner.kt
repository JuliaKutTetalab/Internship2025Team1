package com.arathort.growbox.data.remote.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import com.arathort.growbox.domain.ble.BleScanner
import com.arathort.growbox.domain.models.device.ScannedDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AndroidBleScanner @Inject constructor(
    @ApplicationContext private val context: Context
) : BleScanner {
    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val scanner = bluetoothAdapter?.bluetoothLeScanner

    @SuppressLint("MissingPermission")
    override fun scan(): Flow<List<ScannedDevice>> = callbackFlow {
        val foundDevices = mutableMapOf<String, ScannedDevice>()

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(0)
            .build()

        val callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                super.onScanResult(callbackType, result)

                val name = result.device.name ?: result.scanRecord?.deviceName

                val device = ScannedDevice(
                    name = name ?: "No name",
                    address = result.device.address,
                    rssi = result.rssi
                )

                foundDevices[device.address] = device

                trySend(foundDevices.values.toList())
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
            }

            override fun onScanFailed(errorCode: Int) {
                super.onScanFailed(errorCode)
                close(Exception("Scan failed with error: $errorCode"))
            }
        }

        scanner?.startScan(null, settings, callback)

        awaitClose {
            scanner?.stopScan(callback)
        }
    }
}