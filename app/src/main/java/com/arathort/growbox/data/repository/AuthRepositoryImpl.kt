package com.arathort.growbox.data.repository

import com.arathort.growbox.data.remote.FirebaseAuthDataSource
import com.arathort.growbox.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
) : AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> {
        return firebaseAuthDataSource.signIn(email, password)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> {
        return firebaseAuthDataSource.signUp(email, password)
    }
}