package com.parsuomash.telescope.presentation

import com.parsuomash.telescope.domain.model.Movie
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class MoviesViewState(
    val movies: PersistentList<Movie> = persistentListOf()
)
