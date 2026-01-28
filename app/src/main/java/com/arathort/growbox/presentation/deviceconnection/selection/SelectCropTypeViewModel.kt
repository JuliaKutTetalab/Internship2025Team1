package com.arathort.growbox.presentation.deviceconnection.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.crop.GetAllCropsUseCase
import com.arathort.growbox.domain.useCase.device.SaveDeviceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCropTypeViewModel @Inject constructor(
    private val saveDeviceUseCase: SaveDeviceUseCase,
    private val getAllCropsUseCase: GetAllCropsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SelectCropTypeUiState())
    val uiState: StateFlow<SelectCropTypeUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(isLoading = true)
        }
        viewModelScope.launch {
            val crops = getAllCropsUseCase()
            _uiState.update { state ->
                state.copy(cropTypes = crops, isLoading = false)
            }
        }
    }

    fun onEvent(event: SelectCropUiEvent) {
        when (event) {
            is SelectCropUiEvent.OnCropChange -> {
                _uiState.value = _uiState.value.copy(selectedCrop = event.crop)
            }

            SelectCropUiEvent.SaveDevice -> {
                _uiState.update { state -> state.copy(isLoading = true) }

                viewModelScope.launch {
                    val selectedCrop = _uiState.value.selectedCrop ?: return@launch
                    if (saveDeviceUseCase(selectedCrop).isSuccess) {
                        _uiState.update { state -> state.copy(isSuccess = true, isLoading = false) }
                    }
                }
            }
        }
    }
}