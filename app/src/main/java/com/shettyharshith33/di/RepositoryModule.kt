package com.shettyharshith33.di

import com.shettyharshith33.firebaseAuth.repository.AuthRepository
import com.shettyharshith33.firebaseAuth.repository.AuthRepositoryImpl
import com.shettyharshith33.firebaseRealTimeDB.repository.RealTimeDbRepository
import com.shettyharshith33.firebaseRealTimeDB.repository.RealTimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesRealTimeRepository(
        repo: RealTimeDbRepository
    ): RealTimeRepository

    @Binds
    abstract fun providesFirebaseAuthRepository(
        repo: AuthRepositoryImpl
    ): AuthRepository
}