package com.arathort.growbox.presentation.home

sealed interface HomeUiEffect {
    data class NavigateToDetail(val route: SensorType) : HomeUiEffect
}