package com.vdcast.mornhousetesttask.data.remote

import com.vdcast.mornhousetesttask.domain.Fact
import com.vdcast.mornhousetesttask.domain.NetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class KtorNetworkDataSource(private val httpClient: HttpClient) : NetworkDataSource {
    override suspend fun getFactAboutNumber(number: Long): Result<Fact> {
        return try {
            val response = httpClient.get("http://numbersapi.com/$number")
            if (response.status == HttpStatusCode.OK) {
                Result.success(Fact(
                    id = null,
                    factNumber = number,
                    factString = response.bodyAsText(),
                    timeOfSearch = "01"
                ))
            } else {
                Result.failure(Exception("Error while fetching data from the server."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRandomFact(): Result<Fact> {
        return try {
            val response = httpClient.get("http://numbersapi.com/random/math")
            if (response.status == HttpStatusCode.OK) {
                Result.success(Fact(
                    id = null,
                    factNumber = 0L,
                    factString = response.bodyAsText(),
                    timeOfSearch = "01"
                ))
            } else {
                Result.failure(Exception("Error while fetching data from the server."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun close() = httpClient.close()
}