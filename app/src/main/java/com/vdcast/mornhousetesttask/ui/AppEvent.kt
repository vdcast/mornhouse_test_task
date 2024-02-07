package com.vdcast.mornhousetesttask.ui

import com.vdcast.mornhousetesttask.domain.Fact

sealed interface AppEvent {
    object GetRandomFact: AppEvent
    data class GetTheFact(val value: Long?): AppEvent
    data class SelectFact(val fact: Fact): AppEvent
    object UnselectFact: AppEvent
    object ShowInputError: AppEvent
    object DismissInputError: AppEvent
    object ShowNetworkError: AppEvent
    object DismissNetworkError: AppEvent
    object ShowProgressIndicator: AppEvent
    object DismissProgressIndicator: AppEvent
    data class UpdateEventForRetry(val eventForRetry: AppEvent): AppEvent
    object ClearHistory: AppEvent
}