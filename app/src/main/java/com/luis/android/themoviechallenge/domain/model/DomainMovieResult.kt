package com.luis.android.themoviechallenge.domain.model

data class DomainMovieResult(
    val popularMovies: List<PopularMovies>,
    val total_pages: Int,
    val page: Int
)