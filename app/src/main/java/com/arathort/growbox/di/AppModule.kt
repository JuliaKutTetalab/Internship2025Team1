package com.arathort.growbox.di

import com.arathort.growbox.data.remote.FirebaseAuthDataSource
import com.arathort.growbox.data.repository.AuthRepositoryImpl
import com.arathort.growbox.domain.repository.AuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideAuthDataSource(
        auth: FirebaseAuth,
    ): FirebaseAuthDataSource =
        FirebaseAuthDataSource(auth)

    @Provides
    fun provideAuthRepository(dataSource: FirebaseAuthDataSource): AuthRepository =
        AuthRepositoryImpl(dataSource)

}