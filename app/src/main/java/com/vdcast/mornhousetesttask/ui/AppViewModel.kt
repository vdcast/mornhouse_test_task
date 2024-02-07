package com.vdcast.mornhousetesttask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vdcast.mornhousetesttask.data.local.toFactEntity
import com.vdcast.mornhousetesttask.domain.LocalDataSource
import com.vdcast.mornhousetesttask.domain.NetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = combine(
        _uiState,
        localDataSource.getFacts()
    ) { uiState, facts ->
        uiState.copy(
            facts = facts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), AppUiState())


    fun onEvent(event: AppEvent) {
        when (event) {
            AppEvent.GetRandomFact -> {
                viewModelScope.launch {
                    onEvent(AppEvent.ShowProgressIndicator)
                    onEvent(AppEvent.DismissNetworkError)
                    onEvent(AppEvent.UpdateEventForRetry(event))
                    networkDataSource.getRandomFact().onSuccess { randomFact ->
                        val numberFromString =
                            randomFact.factString.split(" ").firstOrNull()?.toLongOrNull()
                        val updatedFact = randomFact.copy(
                            factNumber = numberFromString ?: 0
                        )
                        localDataSource.insert(updatedFact.toFactEntity())

                        _uiState.update { currentState ->
                            currentState.copy(
                                selectedFact = updatedFact,
                                networkError = null
                            )
                        }
                        onEvent(AppEvent.DismissProgressIndicator)
                    }.onFailure { error ->
                        onEvent(AppEvent.ShowNetworkError)
                        onEvent(AppEvent.DismissProgressIndicator)
                    }
                }
            }

            is AppEvent.GetTheFact -> {
                viewModelScope.launch {
                    event.value?.let { number ->
                        onEvent(AppEvent.ShowProgressIndicator)
                        onEvent(AppEvent.UpdateEventForRetry(event))
                        networkDataSource.getFactAboutNumber(number).onSuccess { fact ->

                            val numberFromString =
                                fact.factString.split(" ").firstOrNull()?.toLongOrNull()
                            val updatedFact = fact.copy(
                                factNumber = numberFromString ?: 0
                            )

                            localDataSource.insert(updatedFact.toFactEntity())

                            _uiState.update {
                                it.copy(
                                    selectedFact = updatedFact,
                                    networkError = null
                                )
                            }
                            onEvent(AppEvent.DismissProgressIndicator)
                        }.onFailure { error ->
                            onEvent(AppEvent.ShowNetworkError)
                            onEvent(AppEvent.DismissProgressIndicator)
                        }
                    } ?: kotlin.run {
                        onEvent(AppEvent.ShowInputError)
                    }
                }

            }

            AppEvent.ShowInputError -> {
                _uiState.update {
                    it.copy(
                        inputError = "Please, input a number."
                    )
                }
            }

            AppEvent.DismissInputError -> {
                _uiState.update {
                    it.copy(
                        inputError = null
                    )
                }
            }

            AppEvent.DismissNetworkError -> {
                _uiState.update {
                    it.copy(
                        networkError = null
                    )
                }
            }

            AppEvent.ShowNetworkError -> {
                _uiState.update {
                    it.copy(
                        networkError = "Error getting data from server. Please, check network connection."
                    )
                }
            }

            AppEvent.ShowProgressIndicator -> {
                _uiState.update {
                    it.copy(
                        factIsLoading = true
                    )
                }
            }

            AppEvent.DismissProgressIndicator -> {
                _uiState.update {
                    it.copy(
                        factIsLoading = false
                    )
                }
            }

            is AppEvent.SelectFact -> {
                _uiState.update {
                    it.copy(
                        selectedFact = event.fact
                    )
                }
            }

            AppEvent.UnselectFact -> {
                _uiState.update {
                    it.copy(
                        selectedFact = null
                    )
                }
            }

            is AppEvent.UpdateEventForRetry -> {
                _uiState.update {
                    it.copy(
                        eventForRetry = event.eventForRetry
                    )
                }
            }

            AppEvent.ClearHistory -> {
                viewModelScope.launch {
                    localDataSource.deleteAll()
                }
            }
        }
    }
}