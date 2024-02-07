package com.vdcast.mornhousetesttask.data.local

import com.vdcast.mornhousetesttask.domain.Fact

fun FactEntity.toFact(): Fact {
    return Fact(
        id = id,
        factNumber = factNumber,
        factString = factString,
        timeOfSearch = timeOfSearch
    )
}

fun Fact.toFactEntity(): FactEntity {
    return FactEntity(
        id = 0,
        factNumber = factNumber,
        factString = factString,
        timeOfSearch = timeOfSearch
    )
}