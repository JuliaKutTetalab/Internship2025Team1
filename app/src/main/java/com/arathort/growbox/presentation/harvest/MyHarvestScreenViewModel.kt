package com.arathort.growbox.presentation.harvest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.crop.GetAllCropsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyHarvestScreenViewModel @Inject constructor(
    private val getAllCropsUseCase: GetAllCropsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyHarvestScreenUiState())
    val uiState: StateFlow<MyHarvestScreenUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { uiState -> uiState.copy(isLoading = true) }
        viewModelScope.launch {
            val crops = getAllCropsUseCase()
            _uiState.update { screenUiState ->
                screenUiState.copy(
                    crops = crops,
                    isLoading = false
                )
            }
        }
    }

}