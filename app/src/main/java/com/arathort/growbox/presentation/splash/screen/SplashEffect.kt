package com.arathort.growbox.presentation.splash.screen

sealed interface SplashEffect {
    data object NavigateToHome: SplashEffect
    data object NavigateToLogin: SplashEffect
}