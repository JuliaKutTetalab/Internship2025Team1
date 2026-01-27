package com.arathort.growbox.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import com.arathort.growbox.domain.useCase.onboarding.SaveUserEntryUseCase
import com.arathort.growbox.presentation.splash.screen.SplashEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingScreenViewModel @Inject constructor(
    private val saveUserEntryUseCase: SaveUserEntryUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase
) : ViewModel() {


    private val _sideEffects = Channel<OnBoardingEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: OnBoardingUiEvent) {
        when (event) {
            is OnBoardingUiEvent.FinishOnBoarding -> {
                viewModelScope.launch {
                    saveUserEntryUseCase()
                    val hasDevice = getDeviceStateUseCase()
                    if (hasDevice.isSuccess && hasDevice.getOrNull() == null) {
                        _sideEffects.send(OnBoardingEffect.NavigateToConnection)
                        return@launch
                    }
                    _sideEffects.send(OnBoardingEffect.NavigateToHome)
                }
            }
        }
    }
}