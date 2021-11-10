package com.luis.android.themoviechallenge.data.helpers

import com.luis.android.themoviechallenge.core.mapper.Mapper
import com.luis.android.themoviechallenge.data.db.entity.PopularMoviesEntity
import com.luis.android.themoviechallenge.domain.model.PopularMovies

class PopularMoviesEntityToPopularMovies : Mapper<PopularMoviesEntity, PopularMovies>() {

    override fun map(value: PopularMoviesEntity): PopularMovies {
        return PopularMovies(value.movieId,value.title,value.posterImagePath)
    }

    override fun reverseMap(value: PopularMovies): PopularMoviesEntity {
        return PopularMoviesEntity(value.movieId,value.title,value.posterImagePath)
    }
}