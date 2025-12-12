package com.arathort.growbox.presentation.onboarding

sealed class OnBoardingUiEvent {
    data object FinishOnBoarding: OnBoardingUiEvent()
}