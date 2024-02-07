package com.vdcast.mornhousetesttask.domain

interface NetworkDataSource {
    suspend fun getFactAboutNumber(number: Long): Result<Fact>
    suspend fun getRandomFact(): Result<Fact>
    fun close()
}