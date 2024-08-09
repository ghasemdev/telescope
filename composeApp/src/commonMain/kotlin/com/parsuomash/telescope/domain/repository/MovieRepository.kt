package com.parsuomash.telescope.domain.repository

import com.parsuomash.telescope.domain.model.Movie
import com.parsuomash.telescope.domain.usecase.GetMoviesUsecase

interface MovieRepository {
    suspend fun getMovies(param: GetMoviesUsecase.Param): List<Movie>
}
