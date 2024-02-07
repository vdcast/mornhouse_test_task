package com.vdcast.mornhousetesttask.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(factEntity: FactEntity)

    @Update
    suspend fun update(factEntity: FactEntity)

    @Delete
    suspend fun delete(factEntity: FactEntity)

    @Query("SELECT * FROM facts_table ORDER BY id DESC")
    fun getFacts(): Flow<List<FactEntity>>

    @Query("SELECT * FROM facts_table WHERE id = :id")
    suspend fun getFactById(id: Int): FactEntity?
}