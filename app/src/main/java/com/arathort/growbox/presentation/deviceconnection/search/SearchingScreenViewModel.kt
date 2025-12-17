package com.arathort.growbox.presentation.deviceconnection.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchingScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchingScreenUiState())
    val uiState: StateFlow<SearchingScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: SearchingScreenUiEvent) {
        when (event) {
            SearchingScreenUiEvent.DeviceFound -> {
                _uiState.value = _uiState.value.copy(isFound = true)
            }
        }
    }
}