package com.arathort.growbox.presentation.auth.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> _uiState.update { it.copy(email = event.email) }
            is LoginUiEvent.OnPasswordChanged -> _uiState.update { it.copy(password = event.password) }
            LoginUiEvent.OnTogglePasswordVisibilityClicked -> _uiState.update {
                it.copy(
                    isPasswordVisible = !it.isPasswordVisible
                )
            }
            LoginUiEvent.OnSignInClicked -> {
                TODO()
            }

            LoginUiEvent.OnSignUpClicked -> {
                TODO()
            }
        }
    }
}