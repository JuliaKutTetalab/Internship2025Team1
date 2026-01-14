package com.arathort.growbox.data.repository

import android.util.Log
import com.arathort.growbox.data.remote.dto.device.DeviceSettingsDto
import com.arathort.growbox.data.remote.dto.device.DeviceStateDto
import com.arathort.growbox.data.remote.dto.device.toDomain
import com.arathort.growbox.data.remote.dto.device.toDto
import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.models.device.DeviceState
import com.arathort.growbox.domain.repository.DeviceRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : DeviceRepository {

    override suspend fun getDeviceState(deviceId: String): DeviceState? {
        return try {
            firestore.collection("devices")
                .document(deviceId)
                .get()
                .await()
                .toObject(DeviceStateDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getUserDevices(userId: String): List<DeviceState> {
        return try {
            firestore.collection("devices")
                .whereEqualTo("owner_id", userId)
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(DeviceStateDto::class.java)?.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getDeviceSettings(deviceId: String): DeviceSettings? {
        val settings = try {
            firestore.collection("device_settings")
                .document(deviceId)
                .get()
                .await()
                .toObject(DeviceSettingsDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            e.message?.let { Log.e("My tag", it) }
            null
        }

        return settings
    }

    override suspend fun saveDeviceSettings(settings: DeviceSettings) {
        val dto = settings.toDto()
        firestore.collection("device_settings")
            .document(settings.deviceId)
            .set(dto)
            .await()
    }
    override suspend fun sendDeviceCommand(deviceId: String, turnVentOn: Boolean?, turnWateringOn: Boolean?) {
        val updates = mutableMapOf<String, Any>()

        if (turnVentOn != null) updates["is_vent_running"] = turnVentOn
        if (turnWateringOn != null) updates["is_watering_running"] = turnWateringOn

        if (updates.isNotEmpty()) {
            firestore.collection("devices")
                .document(deviceId)
                .update(updates)
                .await()
        }
    }
}