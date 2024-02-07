package com.vdcast.mornhousetesttask.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts_table")
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val factNumber: Long = 0L,
    val factString: String = "",
    val timeOfSearch: String = "01-01-2001"
)
