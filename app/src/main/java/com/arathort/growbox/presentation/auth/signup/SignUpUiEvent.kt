package com.arathort.growbox.presentation.auth.signup


sealed class SignUpUiEvent {
    data class OnEmailChanged(val email: String) : SignUpUiEvent()
    data class OnPasswordChanged(val password: String) : SignUpUiEvent()
    data class OnConfirmPasswordChanged(val password: String) : SignUpUiEvent()
    object OnTogglePasswordVisibilityClicked : SignUpUiEvent()
    object OnToggleConfirmPasswordVisibilityClicked : SignUpUiEvent()
    object OnSignUpClicked : SignUpUiEvent()
}