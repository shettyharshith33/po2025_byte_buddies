package com.shettyharshith33.firebaseAuth.repository

import com.shettyharshith33.firebaseAuth.AuthUser
import com.shettyharshith33.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUser(
        authUser: AuthUser
    ): Flow<ResultState<String>>


    fun loginUser(
        authUser: AuthUser
    ): Flow<ResultState<String>>
}


//    fun createUserWithPhone(
//        phone:String,
//        activity:Activity
//    ) :Flow<ResultState<String>>
//
//
//    fun signInWithCredential(
//        otp:String) :Flow<ResultState<String>>
//}