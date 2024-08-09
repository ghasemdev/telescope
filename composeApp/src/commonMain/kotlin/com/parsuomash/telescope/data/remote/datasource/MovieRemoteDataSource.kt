package com.parsuomash.telescope.data.remote.datasource

import com.parsuomash.telescope.data.remote.api.MovieApi
import com.parsuomash.telescope.data.remote.model.MovieResponse

interface MovieRemoteDataSource {
    suspend fun getMovies(page: Int): List<MovieResponse>
}

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApi
) : MovieRemoteDataSource {
    override suspend fun getMovies(page: Int): List<MovieResponse> {
        return movieApi.getMovies(page).data
    }
}
