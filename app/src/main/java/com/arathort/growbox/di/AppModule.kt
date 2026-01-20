package com.arathort.growbox.di

import com.arathort.growbox.data.remote.FirebaseAuthDataSource
import com.arathort.growbox.data.repository.AuthRepositoryImpl
import com.arathort.growbox.data.repository.DeviceRepositoryImpl
import com.arathort.growbox.data.repository.UserRepositoryImpl
import com.arathort.growbox.domain.repository.AuthRepository
import com.arathort.growbox.domain.repository.DeviceRepository
import com.arathort.growbox.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideAuthDataSource(
        auth: FirebaseAuth,
    ): FirebaseAuthDataSource =
        FirebaseAuthDataSource(auth)

    @Provides
    fun provideAuthRepository(dataSource: FirebaseAuthDataSource): AuthRepository =
        AuthRepositoryImpl(dataSource)


    @Provides
    fun provideDeviceRepository(firebaseFirestore: FirebaseFirestore): DeviceRepository =
        DeviceRepositoryImpl(firestore = firebaseFirestore)

    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): UserRepository =
        UserRepositoryImpl(
            firestore = firebaseFirestore, firebaseAuth = firebaseAuth
        )

}