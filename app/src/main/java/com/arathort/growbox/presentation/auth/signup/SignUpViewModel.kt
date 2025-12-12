package com.arathort.growbox.presentation.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arathort.growbox.R
import com.arathort.growbox.domain.useCase.auth.SignUpUseCase
import com.arathort.growbox.presentation.auth.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
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
                        passwordError = null,
                        isSamePasswords = true
                    )
                }
            }

            is SignUpUiEvent.OnConfirmPasswordChanged -> {
                val isValid = InputValidator.isValidPassword(event.password)
                _uiState.update {
                    it.copy(
                        confirmPassword = event.password,
                        isConfirmPasswordValid = isValid,
                        confirmPasswordError = null,
                        isSamePasswords = true
                    )
                }
            }

            SignUpUiEvent.OnTogglePasswordVisibilityClicked -> {
                _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            SignUpUiEvent.OnToggleConfirmPasswordVisibilityClicked -> {
                _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
            }

            SignUpUiEvent.OnSignUpClicked -> validateAndSignUp()
        }
    }

    private fun validateAndSignUp() {
        val state = _uiState.value
        val isEmailValid = InputValidator.isValidEmail(state.email)
        val isPasswordValid = InputValidator.isValidPassword(state.password)
        val isConfirmPasswordValid = InputValidator.isValidPassword(state.confirmPassword)
        if (!isEmailValid || !isPasswordValid || !isConfirmPasswordValid) {
            _uiState.update {
                it.copy(
                    emailError = if (!isEmailValid) R.string.error_invalid_email else null,
                    passwordError = if (!isPasswordValid) R.string.error_password_requirements else null,
                    confirmPasswordError = if (!isConfirmPasswordValid) R.string.error_password_requirements else null
                )
            }
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.update {
                it.copy(
                    isSamePasswords = false
                )
            }
            return
        }

        performSignUp()
    }

    private fun performSignUp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, firebaseErrorMessage = null) }

            val result = signUpUseCase(_uiState.value.email, _uiState.value.password)

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