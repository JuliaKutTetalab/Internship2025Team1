package com.arathort.growbox.presentation.changeCrop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.crop.GetCropByIdUseCase
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeCropScreenViewModel @Inject constructor(
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
    private val getCropByIdUseCase: GetCropByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChangeCropScreenUiState())
    val uiState: StateFlow<ChangeCropScreenUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { uiState -> uiState.copy(isLoading = true) }
        viewModelScope.launch {
            val state = getDeviceStateUseCase().getOrNull()

            if (state != null && state.activeCropTypeId != null) {
                val crop = getCropByIdUseCase(state.activeCropTypeId)
                val now = System.currentTimeMillis()
                val startDate = state.startDateTimestamp ?: now
                val diffInMillis = now - startDate
                val calculatedDaysGrown =
                    (diffInMillis / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(0)

                _uiState.update { screenUiState ->
                    screenUiState.copy(
                        cropType = crop,
                        daysFromPlant = calculatedDaysGrown,
                        isLoading = false
                    )
                }

            }

        }
        _uiState.update { uiState -> uiState.copy(isLoading = false) }

    }
}