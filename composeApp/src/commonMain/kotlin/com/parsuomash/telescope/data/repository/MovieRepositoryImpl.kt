package com.parsuomash.telescope.data.repository

import com.parsuomash.telescope.data.remote.datasource.MovieRemoteDataSource
import com.parsuomash.telescope.data.remote.mapper.toMovie
import com.parsuomash.telescope.domain.model.Movie
import com.parsuomash.telescope.domain.repository.MovieRepository
import com.parsuomash.telescope.domain.usecase.GetMoviesUsecase

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getMovies(param: GetMoviesUsecase.Param): List<Movie> {
        return movieRemoteDataSource
            .getMovies(param.page)
            .map { it.toMovie() }
    }
}
