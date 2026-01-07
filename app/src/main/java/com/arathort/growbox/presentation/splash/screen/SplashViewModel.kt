package com.arathort.growbox.presentation.splash.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.domain.repository.AuthRepository
import com.arathort.growbox.domain.useCase.auth.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
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
                _effect.send(SplashEffect.NavigateToHome)
            }else{
                _effect.send(SplashEffect.NavigateToLogin)
            }
        }
    }

}