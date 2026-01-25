package com.arathort.growbox.presentation.onboarding

sealed interface OnBoardingEffect {
    data object NavigateToConnection : OnBoardingEffect
    data object NavigateToHome : OnBoardingEffect
}