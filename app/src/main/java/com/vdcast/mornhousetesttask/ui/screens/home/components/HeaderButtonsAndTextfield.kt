package com.vdcast.mornhousetesttask.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vdcast.mornhousetesttask.ui.AppEvent
import com.vdcast.mornhousetesttask.ui.AppUiState

@Composable
fun HeaderButtonsAndTextfield(
    modifier: Modifier = Modifier,
    uiState: AppUiState,
    onEvent: (AppEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        TextAndProgressBar(uiState = uiState)
        Spacer(modifier = Modifier.weight(1f))
        GetRandomNumberFactRow(onEvent = onEvent)
        Spacer(modifier = Modifier.weight(1f))
        GetFactForNumberRow(onEvent = onEvent)
        Spacer(modifier = Modifier.weight(1f))
    }
}