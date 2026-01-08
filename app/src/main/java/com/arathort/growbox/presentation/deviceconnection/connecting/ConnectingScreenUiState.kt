package com.arathort.growbox.presentation.deviceconnection.connecting

import com.arathort.growbox.presentation.deviceconnection.search.MockGrowBox

data class ConnectingScreenUiState(
    val isConnected: Boolean = false,
    val growBox: MockGrowBox = MockGrowBox()
)
