package com.parsuomash.telescope.data.remote.model

import com.parsuomash.telescope.domain.model.URL
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val id: Int,
    val title: String,
    val poster: URL,
    val year: String,
    val country: String,
    @SerialName("imdb_rating") val imdbRating: String,
    val genres: List<String>,
    val images: List<URL>
)
