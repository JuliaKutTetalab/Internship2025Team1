package com.arathort.growbox.presentation.deviceconnection.connecting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ConnectingScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(ConnectingScreenUiState())
    val uiState: StateFlow<ConnectingScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: ConnectingScreenUiEvent) {
        when (event) {
            ConnectingScreenUiEvent.DeviceConnected -> {
                _uiState.value = _uiState.value.copy(isConnected = true)
            }
        }
    }
}