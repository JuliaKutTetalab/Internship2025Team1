package com.arathort.growbox.presentation.auth.login

sealed class LoginUiEvent {
    data class OnEmailChanged(val email: String) : LoginUiEvent()
    data class OnPasswordChanged(val password: String) : LoginUiEvent()
    object OnTogglePasswordVisibilityClicked : LoginUiEvent()
    object OnSignInClicked : LoginUiEvent()
    object OnSignUpClicked : LoginUiEvent()
}