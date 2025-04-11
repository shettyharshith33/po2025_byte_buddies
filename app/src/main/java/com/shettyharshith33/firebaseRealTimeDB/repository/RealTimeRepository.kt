package com.shettyharshith33.firebaseRealTimeDB.repository

import com.shettyharshith33.firebaseRealTimeDB.RealTimeModelResponse
import com.shettyharshith33.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface RealTimeRepository {
    fun insert(
        item: RealTimeModelResponse.RealTimeItems
    ): Flow<ResultState<String>>

    fun getItems(): Flow<ResultState<List<RealTimeModelResponse>>>

    fun delete(
        key: String
    ): Flow<ResultState<String>>

    fun update(
        res:RealTimeModelResponse
    ) :Flow<ResultState<String>>
}