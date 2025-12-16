package com.arathort.growbox.domain.useCase.auth

import com.arathort.growbox.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.signIn(email, password)
    }
}