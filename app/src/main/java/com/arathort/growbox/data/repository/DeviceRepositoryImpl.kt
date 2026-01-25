package com.arathort.growbox.data.repository

import android.util.Log
import com.arathort.growbox.data.remote.dto.device.DeviceSettingsDto
import com.arathort.growbox.data.remote.dto.device.DeviceStateDto
import com.arathort.growbox.data.remote.dto.device.toDomain
import com.arathort.growbox.data.remote.dto.device.toDto
import com.arathort.growbox.domain.models.device.DeviceSettings
import com.arathort.growbox.domain.models.device.DeviceState
import com.arathort.growbox.domain.models.library.CropType
import com.arathort.growbox.domain.repository.DeviceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : DeviceRepository {

    override suspend fun getDeviceState(): Result<DeviceState?> {
        val ownerId = firebaseAuth.uid ?: return Result.failure(exception = NullPointerException())
        return try {
            val device = firestore.collection("devices")
                .document("device_$ownerId")
                .get()
                .await()
                .toObject(DeviceStateDto::class.java)
                ?.toDomain()
            Result.success(device)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
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

    override suspend fun getDeviceSettings(): DeviceSettings? {
        val ownerId = firebaseAuth.uid ?: return null
        val settings = try {
            firestore.collection("device_settings")
                .document("device_$ownerId")
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

    override suspend fun sendDeviceCommand(
        turnVentOn: Boolean?,
        turnWateringOn: Boolean?
    ) {
        val updates = mutableMapOf<String, Any>()
        val ownerId = firebaseAuth.uid ?: return

        if (turnVentOn != null) updates["_vent_running"] = turnVentOn
        if (turnWateringOn != null) updates["_watering_running"] = turnWateringOn

        if (updates.isNotEmpty()) {
            firestore.collection("devices")
                .document("device_$ownerId")
                .update(updates)
                .await()
        }
    }

    override suspend fun saveDevice(cropType: CropType): Result<Unit> {
        val ownerId = firebaseAuth.uid ?: return Result.failure(exception = NullPointerException())
        val device = DeviceState(
            deviceId = "device_$ownerId",
            ownerId = ownerId,
            activeCropTypeId = cropType.id,
            activeCropName = cropType.name,
            activeCropImageUrl = "",
            startDateTimestamp = System.currentTimeMillis(),
            estimatedHarvestDays = null,
            lastUpdated = System.currentTimeMillis(),
            currentTemperature = 0.0,
            currentHumidity = 0.0,
            currentLightLevel = 0.0,
            currentNutritionLevel = 0.0,
            isVentRunning = false,
            isWateringRunning = false,
        )
        val data = device.toDto()
        firestore.collection("devices")
            .document(data.device_id)
            .set(data)
            .await()
        val deviceSettings = DeviceSettings(
            deviceId = "device_$ownerId",
            isLightAutomationEnabled = true,
            isVentAutomationEnabled = true,
            ventDurationHours = 12.0,
            lightDurationHours = 8.0,
            targetHumidity = 50.0,
            targetTemperature = 24.0,
            nutritionTargetAmount = 250.0,
            wateringTargetAmount = 250.0,
            wateringFrequencyIndex = 0,
            nutritionFrequencyIndex = 0
        ).toDto()
        firestore.collection("device_settings")
            .document(deviceSettings.device_id)
            .set(deviceSettings)
            .await()
        return Result.success(Unit)
    }
}