package com.arathort.growbox.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.R
import com.arathort.growbox.domain.useCase.auth.SignInUseCase
import com.arathort.growbox.presentation.auth.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> {
                val isValid = InputValidator.isValidEmail(event.email)
                _uiState.update {
                    it.copy(
                        email = event.email,
                        isEmailValid = isValid,
                        emailError = null
                    )
                }
            }
            is LoginUiEvent.OnPasswordChanged -> {
                val isValid = InputValidator.isValidPassword(event.password)
                _uiState.update {
                    it.copy(
                        password = event.password,
                        isPasswordValid = isValid,
                        passwordError = null
                    )
                }
            }
            LoginUiEvent.OnTogglePasswordVisibilityClicked -> {
                _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            LoginUiEvent.OnSignInClicked -> validateAndSignIn()
        }
    }

    private fun validateAndSignIn() {
        val state = _uiState.value
        val isEmailValid = InputValidator.isValidEmail(state.email)
        val isPasswordValid = InputValidator.isValidPassword(state.password)

        if (!isEmailValid || !isPasswordValid) {
            _uiState.update {
                it.copy(
                    emailError = if (!isEmailValid) R.string.error_invalid_email else null,
                    passwordError = if (!isPasswordValid) R.string.error_password_requirements else null
                )
            }
            return
        }

        performSignIn()
    }

    private fun performSignIn() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, firebaseErrorMessage = null) }

            val result = signInUseCase(_uiState.value.email, _uiState.value.password)

            _uiState.update {
                if (result.isSuccess) {
                    it.copy(isLoading = false, isSuccess = true)
                } else {
                    it.copy(
                        isLoading = false,
                        firebaseErrorMessage = result.exceptionOrNull()?.message
                    )
                }
            }
        }
    }
}