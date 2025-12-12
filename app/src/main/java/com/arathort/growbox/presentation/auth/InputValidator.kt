package com.arathort.growbox.presentation.auth

import com.arathort.growbox.R

object InputValidator {
    private val EMAIL_PATTERN = android.util.Patterns.EMAIL_ADDRESS
    private val PASSWORD_PATTERN = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}$")

    fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && EMAIL_PATTERN.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return PASSWORD_PATTERN.matches(password)
    }

    fun getPasswordErrorIdOrNull(password: String): Int? {
        return if (!isValidPassword(password)) R.string.error_password_requirements else null
    }
}