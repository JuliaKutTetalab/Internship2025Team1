package com.arathort.growbox.presentation.auth.signup

import androidx.lifecycle.ViewModel
import com.arathort.growbox.presentation.auth.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.OnEmailChanged -> {
                val isValid = InputValidator.isValidEmail(event.email)
                _uiState.update {
                    it.copy(
                        email = event.email,
                        isEmailValid = isValid,
                        emailError = null
                    )
                }
            }

            is SignUpUiEvent.OnPasswordChanged -> {
                val isValid = InputValidator.isValidPassword(event.password)
                _uiState.update {
                    it.copy(
                        password = event.password,
                        isPasswordValid = isValid,
                        passwordError = null
                    )
                }
            }

            is SignUpUiEvent.OnConfirmPasswordChanged -> {
                val isValid = InputValidator.isValidPassword(event.password)
                _uiState.update {
                    it.copy(
                        confirmPassword = event.password,
                        isConfirmPasswordValid = isValid,
                        confirmPasswordError = null
                    )
                }
            }
            SignUpUiEvent.OnTogglePasswordVisibilityClicked -> {
                _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }
            SignUpUiEvent.OnToggleConfirmPasswordVisibilityClicked ->{
                _uiState.update { it.copy(isConfirmPasswordVisible = !it.isPasswordVisible) }
            }
            SignUpUiEvent.OnSignUpClicked -> TODO()
        }
    }
}