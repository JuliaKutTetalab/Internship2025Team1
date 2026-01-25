package com.arathort.growbox.presentation.splash.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.useCase.auth.IsUserLoggedInUseCase
import com.arathort.growbox.domain.useCase.device.GetDeviceStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase
) : ViewModel() {

    private val _effect = Channel<SplashEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        onEvent(SplashUiEvent.StartLoading)
    }

    fun onEvent(event: SplashUiEvent) {
        when (event) {
            SplashUiEvent.StartLoading -> startAuthCheck()
        }
    }
    private fun startAuthCheck() {
        viewModelScope.launch {
            val authCheckDeferred = async {
                isUserLoggedInUseCase()
            }

            val minDelay = async {
                delay(1500)
            }

            minDelay.await()

            val isLoggedIn = authCheckDeferred.await()

            if (isLoggedIn){
                val hasDevice = getDeviceStateUseCase()
                if(hasDevice.isSuccess && hasDevice.getOrNull()==null){
                    _effect.send(SplashEffect.NavigateToConnection)
                    return@launch
                }
                _effect.send(SplashEffect.NavigateToHome)
            }else{
                _effect.send(SplashEffect.NavigateToLogin)
            }
        }
    }

}