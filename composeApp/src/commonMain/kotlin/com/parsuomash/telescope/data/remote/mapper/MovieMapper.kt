package com.parsuomash.telescope.data.remote.mapper

import com.parsuomash.telescope.data.remote.model.MovieResponse
import com.parsuomash.telescope.domain.model.Movie

fun MovieResponse.toMovie(): Movie = Movie(
    id = id,
    title = title,
    poster = poster,
    year = year,
    country = country,
    imdbRating = imdbRating.toFloatOrNull(),
    genres = genres,
    images = images
)
