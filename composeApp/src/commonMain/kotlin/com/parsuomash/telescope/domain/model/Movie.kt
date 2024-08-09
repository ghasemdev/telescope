package com.parsuomash.telescope.domain.model

typealias URL = String

data class Movie(
    val id: Int,
    val title: String,
    val poster: URL,
    val year: String,
    val country: String,
    val imdbRating: Float?,
    val genres: List<String>,
    val images: List<URL>
)
