package com.arathort.growbox.presentation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val firebaseErrorMessage: String? = null,
    val emailError: Int? = null,
    val passwordError: Int? = null,
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false
)
