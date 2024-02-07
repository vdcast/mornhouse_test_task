package com.vdcast.mornhousetesttask.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.vdcast.mornhousetesttask.R
import com.vdcast.mornhousetesttask.ui.AppEvent
import kotlinx.coroutines.launch

@Composable
fun GetRandomNumberFactRow(
    onEvent: (AppEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val debouncePeriod = 1000L

    var lastClickedButtonTime by remember { mutableStateOf(0L) }
    Row {
        Button(
            onClick = {
                coroutineScope.launch {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickedButtonTime >= debouncePeriod) {
                        lastClickedButtonTime = currentTime
                        onEvent(AppEvent.GetRandomFact)
                    }
                }
            },

            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.random_fact))
        }
    }
}