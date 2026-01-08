package com.arathort.growbox.presentation.home

data class HomeUiState(
    val isLoading: Boolean=false,
    val cropName:String="",
    val daysGrown:Int=0,
    val totalDays:Int=0,
    val temperature: Int=0,
    val humidity:Int=0,
    val lightLevel:Int=0,
    val nutritionLevel:Int=0,
    val isVentOn: Boolean=false,
    val isWateringOn:Boolean=false,
    val progress:Float=0f,
    val connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED
){
    val daysRemaining: Int
        get() = (totalDays - daysGrown).coerceAtLeast(0)
}

enum class ConnectionStatus {
    CONNECTED, CONNECTING, DISCONNECTED
}