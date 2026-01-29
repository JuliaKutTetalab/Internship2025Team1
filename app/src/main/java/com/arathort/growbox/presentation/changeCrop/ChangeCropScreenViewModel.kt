package com.arathort.growbox.presentation.changeCrop

import androidx.lifecycle.ViewModel
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ChangeCropScreenViewModel @Inject constructor(
    private val getDeviceStateUseCase: GetDeviceStateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChangeCropScreenUiState())
    val uiState: StateFlow<ChangeCropScreenUiState> = _uiState.asStateFlow()

    init {

    }
}