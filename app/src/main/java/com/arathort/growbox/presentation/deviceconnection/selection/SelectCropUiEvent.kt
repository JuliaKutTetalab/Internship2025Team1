package com.arathort.growbox.presentation.deviceconnection.selection

import com.arathort.growbox.domain.models.library.CropType

sealed class SelectCropUiEvent {
    data class OnCropChange(val crop: CropType): SelectCropUiEvent()
    data object SaveDevice: SelectCropUiEvent()
}