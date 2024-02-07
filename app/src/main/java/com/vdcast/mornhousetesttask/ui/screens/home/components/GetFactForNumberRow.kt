package com.vdcast.mornhousetesttask.ui.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.vdcast.mornhousetesttask.R
import com.vdcast.mornhousetesttask.ui.AppEvent
import kotlinx.coroutines.launch

@Composable
fun GetFactForNumberRow(
    onEvent: (AppEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val debouncePeriod = 1000L

    var lastClickedButtonTime by remember { mutableStateOf(0L) }
    var inputNumber by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.padding_small)),
            maxLines = 1,
            value = inputNumber,
            onValueChange = { newValue ->
                inputNumber = newValue
                onEvent(AppEvent.DismissInputError)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (inputNumber.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.input_number),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                    innerTextField()
                }
            },
        )

        Button(
            modifier = Modifier
                .weight(1f),
            onClick = {
                coroutineScope.launch {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastClickedButtonTime >= debouncePeriod) {
                        lastClickedButtonTime = currentTime
                        onEvent(AppEvent.GetTheFact(inputNumber.toLongOrNull()))
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text(text = stringResource(id = R.string.get_the_fact))
        }
    }
}