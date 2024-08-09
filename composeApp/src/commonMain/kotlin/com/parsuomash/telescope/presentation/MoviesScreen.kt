package com.parsuomash.telescope.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MoviesScreen() {
    MoviesScreen(viewModel = koinViewModel())
}

@Composable
private fun MoviesScreen(
    viewModel: MoviesViewModel
) {
    val state: MoviesViewState by viewModel.state.collectAsStateWithLifecycle()

    MoviesScreen(viewState = state)
}

@Composable
private fun MoviesScreen(
    viewState: MoviesViewState
) {
    LazyColumn {
        items(viewState.movies, key = { it.id }) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(it.title)
                }
            }
        }
    }
}
