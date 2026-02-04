package com.arathort.growbox.domain.useCase.auth

import com.arathort.growbox.domain.models.user.UserProfile
import com.arathort.growbox.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userProfile: UserProfile) {
        userRepository.saveUserProfile(userProfile)
    }
}