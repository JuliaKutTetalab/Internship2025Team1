package com.arathort.growbox.presentation.history.chart

interface HistoryChartUiEffect {
    data object OnNavigateToBack: HistoryChartUiEffect
    data object ShowToast : HistoryChartUiEffect
}