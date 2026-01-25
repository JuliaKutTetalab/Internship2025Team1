package com.arathort.growbox.di

import android.app.Application
import com.arathort.growbox.data.providers.UserProvider
import com.arathort.growbox.data.remote.FirebaseAuthDataSource
import com.arathort.growbox.data.repository.AnalyticsRepositoryImpl
import com.arathort.growbox.data.repository.AuthRepositoryImpl
import com.arathort.growbox.data.repository.DeviceRepositoryImpl
import com.arathort.growbox.data.repository.LibraryRepositoryImpl
import com.arathort.growbox.data.repository.UserRepositoryImpl
import com.arathort.growbox.domain.repository.AnalyticsRepository
import com.arathort.growbox.domain.repository.AuthRepository
import com.arathort.growbox.domain.repository.DeviceRepository
import com.arathort.growbox.domain.repository.LibraryRepository
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
    fun provideUserProvider(application: Application): UserProvider {
        return UserProvider(application)
    }

    @Provides
    fun provideAuthRepository(
        dataSource: FirebaseAuthDataSource,
        userProvider: UserProvider
    ): AuthRepository =
        AuthRepositoryImpl(dataSource, userProvider = userProvider)


    @Provides
    fun provideDeviceRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): DeviceRepository =
        DeviceRepositoryImpl(firestore = firebaseFirestore, firebaseAuth = firebaseAuth)

    @Provides
    fun provideAnalyticsRepository(firebaseFirestore: FirebaseFirestore): AnalyticsRepository =
        AnalyticsRepositoryImpl(firestore = firebaseFirestore)

    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): UserRepository =
        UserRepositoryImpl(
            firestore = firebaseFirestore, firebaseAuth = firebaseAuth
        )

    @Provides
    fun provideLibraryRepository(firebaseFirestore: FirebaseFirestore): LibraryRepository =
        LibraryRepositoryImpl(firestore = firebaseFirestore)

}