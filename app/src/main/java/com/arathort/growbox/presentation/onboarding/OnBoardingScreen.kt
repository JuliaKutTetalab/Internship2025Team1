package com.arathort.growbox.presentation.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.arathort.growbox.presentation.onboarding.components.OnBoardingContent

@Composable
fun OnBoardingScreen(
    onBoardingViewModel: OnBoardingScreenViewModel = hiltViewModel(),
    onNavigateToConnection: () -> Unit,
    onNavigateToHome: ()-> Unit
) {

    LaunchedEffect(true) {
        onBoardingViewModel.sideEffects.collect { effect ->
            when (effect) {
                is OnBoardingEffect.NavigateToConnection -> {
                    onNavigateToConnection()
                }

                OnBoardingEffect.NavigateToHome -> {
                    onNavigateToHome()
                }
            }
        }
    }

    OnBoardingContent(
        onEvent = onBoardingViewModel::onEvent
    )
}




@Preview
@Composable
fun Preview() {
    OnBoardingContent(
        onEvent = {}
    )
}