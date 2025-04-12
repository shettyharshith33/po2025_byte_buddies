package com.shettyharshith33.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent ::class)
object FireBaseModule {
    @Provides
    @Singleton
    fun providesRealtimeDb():DatabaseReference =
        Firebase.database.reference.child("user")

    @Singleton
    @Provides
    fun providesFireBaseAuth():FirebaseAuth = Firebase.auth
}