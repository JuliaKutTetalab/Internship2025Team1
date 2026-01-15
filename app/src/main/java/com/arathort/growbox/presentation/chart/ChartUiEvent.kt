package com.arathort.growbox.presentation.chart


sealed interface ChartUiEvent {
    data object OnDailyGraphicSelected : ChartUiEvent
    data object OnWeekGraphicSelected : ChartUiEvent
    data object OnMonthlyGraphicSelected : ChartUiEvent

    data object OnReturnButtonClick : ChartUiEvent
}