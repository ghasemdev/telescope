package com.parsuomash.telescope.domain.usecase

import com.parsuomash.telescope.domain.model.Movie
import com.parsuomash.telescope.domain.repository.MovieRepository

class GetMoviesUsecase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(param: Param): List<Movie> {
        return movieRepository.getMovies(param)
    }

    data class Param(val page: Int)
}
