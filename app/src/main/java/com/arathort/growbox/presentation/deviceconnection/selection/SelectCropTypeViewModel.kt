package com.arathort.growbox.presentation.deviceconnection.selection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectCropTypeViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SelectCropTypeUiState())
    val uiState: StateFlow<SelectCropTypeUiState> = _uiState.asStateFlow()

    fun onEvent(event: SelectCropUiEvent) {
        when (event) {
            is SelectCropUiEvent.OnCropChange -> {
                _uiState.value = _uiState.value.copy(selectedCrop = event.crop)
            }
        }
    }
}