package com.vdcast.mornhousetesttask.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vdcast.mornhousetesttask.R
import com.vdcast.mornhousetesttask.domain.Fact

@Composable
fun Details(
    selectedFact: Fact?,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedFact?.let { fact ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.fact_about_number, fact.factNumber))
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
                Text(
                    text = fact.factString,
                    textAlign = TextAlign.Start
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_large)))
        Button(onClick = {
            onBack()
        }) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}