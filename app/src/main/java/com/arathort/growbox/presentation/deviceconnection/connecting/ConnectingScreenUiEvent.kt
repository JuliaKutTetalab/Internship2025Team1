package com.arathort.growbox.presentation.deviceconnection.connecting

sealed class ConnectingScreenUiEvent {
    data object DeviceConnected : ConnectingScreenUiEvent()
}