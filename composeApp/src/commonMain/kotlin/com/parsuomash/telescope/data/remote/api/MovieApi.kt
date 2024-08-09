package com.parsuomash.telescope.data.remote.api

import com.parsuomash.telescope.data.remote.api.ApiConstants.GET_MOVIES_URL
import com.parsuomash.telescope.data.remote.model.MovieResponse
import com.parsuomash.telescope.data.remote.model.PaginationResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface MovieApi {
    suspend fun getMovies(page: Int): PaginationResponse<MovieResponse>
}

class MovieApiImpl(
    private val client: HttpClient
) : MovieApi {
    override suspend fun getMovies(page: Int): PaginationResponse<MovieResponse> {
        return client.get(GET_MOVIES_URL) {
            url {
                parameters.append("page", page.toString())
            }
        }.body()
    }
}
