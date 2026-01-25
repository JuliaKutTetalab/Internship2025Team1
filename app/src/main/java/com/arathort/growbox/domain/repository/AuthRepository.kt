package com.arathort.growbox.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>

    fun signOut()
    suspend fun isUserLoggedIn(): Boolean

    fun getUserEntry(): Flow<Boolean>

    suspend fun setUserEntry()

}