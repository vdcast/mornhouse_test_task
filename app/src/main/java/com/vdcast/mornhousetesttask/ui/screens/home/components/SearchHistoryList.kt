package com.vdcast.mornhousetesttask.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.vdcast.mornhousetesttask.R
import com.vdcast.mornhousetesttask.ui.AppEvent
import com.vdcast.mornhousetesttask.ui.AppUiState

@Composable
fun SearchHistoryList(
    modifier: Modifier = Modifier,
    uiState: AppUiState,
    onEvent: (AppEvent) -> Unit,
    navigateToDetailsScreen: () -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.facts) {
        listState.animateScrollToItem(index = 0)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.facts.isEmpty()) {
            Text(
                text = stringResource(id = R.string.history_empty)
            )
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_m_l)))
                    Text(
                        text = stringResource(id = R.string.search_history),
                    )
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.padding_m_l))
                            .clickable {
                                onEvent(AppEvent.ClearHistory)
                            },
                        imageVector = Icons.Default.Delete,
                        contentDescription = "ClearHistory"
                    )

                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Divider()
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = uiState.facts,
                    key = { fact -> fact.id ?: fact.factString }
                ) { fact ->
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clickable {
                                onEvent(AppEvent.SelectFact(fact))
                                navigateToDetailsScreen()
                            },
                    ) {
                        Text(
                            modifier = Modifier.weight(0.5f),
                            text = fact.factNumber.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                        Text(
                            modifier = Modifier.weight(1f),
                            text = fact.factString,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                    Divider()
                }
            }
        }
    }
}