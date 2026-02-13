package com.arathort.growbox.presentation.deviceconnection.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.ble.BleScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingScreenViewModel @Inject constructor(
    private val bleScanner: BleScanner
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchingScreenUiState())
    val uiState: StateFlow<SearchingScreenUiState> = _uiState.asStateFlow()

    fun startScanning() {
        viewModelScope.launch {
            _uiState.update { it.copy(isScanning = true, error = null) }

            bleScanner.scan()
                .catch { e ->
                    _uiState.update { it.copy(isScanning = false, error = e.message) }
                }
                .collect { devices ->
                    _uiState.update {
                        it.copy(
                            scannedDevices = devices.sortedByDescending { it.rssi },
                            isScanning = false
                        )
                    }
                }
        }
    }
}