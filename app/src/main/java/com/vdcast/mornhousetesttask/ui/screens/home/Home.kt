package com.vdcast.mornhousetesttask.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vdcast.mornhousetesttask.ui.AppEvent
import com.vdcast.mornhousetesttask.ui.AppUiState
import com.vdcast.mornhousetesttask.ui.screens.home.components.HeaderButtonsAndTextfield
import com.vdcast.mornhousetesttask.ui.screens.home.components.SearchHistoryList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home(
    uiState: AppUiState,
    onEvent: (AppEvent) -> Unit,
    navigateToDetailsScreen: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = uiState.inputError) {
        if (uiState.inputError != null) {
            snackbarHostState
                .showSnackbar(
                    message = "Please, input a number.",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            onEvent(AppEvent.DismissInputError)
        }
    }

    LaunchedEffect(uiState.networkError) {
        uiState.networkError?.let { errorMessage ->
            val result = snackbarHostState
                .showSnackbar(
                    message = errorMessage,
                    actionLabel = "Retry",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    coroutineScope.launch {
                        delay(1000)
                        uiState.eventForRetry?.let { event ->
                            onEvent(event)
                        }
                        Log.d("MYLOG", "ActionPerformed")
                    }
                }

                SnackbarResult.Dismissed -> {
                    Log.d("MYLOG", "Dismissed")
                }
            }
            onEvent(AppEvent.DismissNetworkError)
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(AppEvent.UnselectFact)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            HeaderButtonsAndTextfield(
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onEvent = onEvent
            )

            SearchHistoryList(
                modifier = Modifier.weight(1f),
                uiState = uiState,
                onEvent = onEvent,
                navigateToDetailsScreen = navigateToDetailsScreen
            )
        }
    }
}