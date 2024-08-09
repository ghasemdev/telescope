package com.parsuomash.telescope.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parsuomash.telescope.domain.usecase.GetMoviesUsecase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    getMoviesUsecase: GetMoviesUsecase
) : ViewModel() {
    private val _state: MutableStateFlow<MoviesViewState> = MutableStateFlow(MoviesViewState())
    val state: StateFlow<MoviesViewState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(movies = getMoviesUsecase(GetMoviesUsecase.Param(page = 1)).toPersistentList())
            }
        }
    }
}
