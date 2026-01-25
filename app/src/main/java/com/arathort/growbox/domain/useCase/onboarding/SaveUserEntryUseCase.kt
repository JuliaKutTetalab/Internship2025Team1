package com.arathort.growbox.domain.useCase.onboarding

import com.arathort.growbox.domain.repository.AuthRepository
import javax.inject.Inject

class SaveUserEntryUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.setUserEntry()
    }
}