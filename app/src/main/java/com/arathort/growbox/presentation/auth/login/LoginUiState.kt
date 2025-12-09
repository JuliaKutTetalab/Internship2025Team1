package com.arathort.growbox.presentation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
)
