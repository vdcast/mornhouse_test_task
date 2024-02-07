package com.vdcast.mornhousetesttask.data.local

import com.vdcast.mornhousetesttask.domain.Fact
import com.vdcast.mornhousetesttask.domain.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalDataSource(private val factDao: FactDao) : LocalDataSource {
    override suspend fun insert(factEntity: FactEntity) = factDao.insert(factEntity)

    override suspend fun update(factEntity: FactEntity) = factDao.update(factEntity)

    override suspend fun delete(factEntity: FactEntity) = factDao.delete(factEntity)
    override suspend fun deleteAll() = factDao.deleteAll()

    override fun getFacts(): Flow<List<Fact>> = factDao.getFacts()
        .map { factEntities ->
            factEntities.map { it.toFact() }
        }

}