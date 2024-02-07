package com.vdcast.mornhousetesttask.domain

import com.vdcast.mornhousetesttask.data.local.FactEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insert(factEntity: FactEntity)
    suspend fun update(factEntity: FactEntity)
    suspend fun delete(factEntity: FactEntity)
    fun getFacts(): Flow<List<Fact>>
    suspend fun getFactById(id: Int): FactEntity?
}