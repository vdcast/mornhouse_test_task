package com.vdcast.mornhousetesttask.ui

import com.vdcast.mornhousetesttask.domain.Fact

data class AppUiState(
    val facts: List<Fact> = emptyList(),
    val selectedFact: Fact? = null,
    val inputError: String? = null,
    val networkError: String? = null,
    val factIsLoading: Boolean = false,
    val eventForRetry: AppEvent? = null,
)
