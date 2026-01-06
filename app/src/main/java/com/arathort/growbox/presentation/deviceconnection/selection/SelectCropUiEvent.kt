package com.arathort.growbox.presentation.deviceconnection.selection

sealed class SelectCropUiEvent {
    data class OnCropChange(val crop: String): SelectCropUiEvent()
}