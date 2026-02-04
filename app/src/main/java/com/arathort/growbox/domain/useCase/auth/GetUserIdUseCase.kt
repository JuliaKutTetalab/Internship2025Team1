package com.arathort.growbox.domain.useCase.auth

import com.arathort.growbox.domain.repository.UserRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String? {
        return userRepository.getUserId()
    }
}