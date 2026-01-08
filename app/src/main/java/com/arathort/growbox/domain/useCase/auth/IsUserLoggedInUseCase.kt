package com.arathort.growbox.domain.useCase.auth

import com.arathort.growbox.domain.repository.AuthRepository
import javax.inject.Inject

class IsUserLoggedInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}