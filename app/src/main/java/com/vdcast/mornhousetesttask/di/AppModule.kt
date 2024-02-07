package com.vdcast.mornhousetesttask.di

import androidx.room.Room
import com.vdcast.mornhousetesttask.data.local.AppDatabase
import com.vdcast.mornhousetesttask.data.local.FactDao
import com.vdcast.mornhousetesttask.data.local.RoomLocalDataSource
import com.vdcast.mornhousetesttask.data.remote.KtorNetworkDataSource
import com.vdcast.mornhousetesttask.domain.LocalDataSource
import com.vdcast.mornhousetesttask.domain.NetworkDataSource
import com.vdcast.mornhousetesttask.ui.AppViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { AppViewModel(get(), get()) }
    single {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }
    single<NetworkDataSource> { KtorNetworkDataSource(get()) }

    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_database")
            .build()
    }
    single<FactDao> { get<AppDatabase>().factDao()}
    single<LocalDataSource> { RoomLocalDataSource(get()) }
}