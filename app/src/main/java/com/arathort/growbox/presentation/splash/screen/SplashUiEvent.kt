package com.arathort.growbox.presentation.splash.screen

sealed class SplashUiEvent {
    data object StartLoading: SplashUiEvent()
}