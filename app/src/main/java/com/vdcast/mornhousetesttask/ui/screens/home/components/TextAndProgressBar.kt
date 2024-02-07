package com.vdcast.mornhousetesttask.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vdcast.mornhousetesttask.R
import com.vdcast.mornhousetesttask.ui.AppUiState

@Composable
fun TextAndProgressBar(
    uiState: AppUiState
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        text = stringResource(id = R.string.home_header_text),
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
    Column(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.padding_xlarge)),
    ) {
        if (uiState.factIsLoading) {
            CircularProgressIndicator()
        }
    }
}