package com.vdcast.mornhousetesttask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}