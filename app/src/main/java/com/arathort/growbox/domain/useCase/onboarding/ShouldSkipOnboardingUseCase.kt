package com.arathort.growbox.domain.useCase.onboarding

import com.arathort.growbox.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShouldSkipOnboardingUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.getUserEntry()
    }
}