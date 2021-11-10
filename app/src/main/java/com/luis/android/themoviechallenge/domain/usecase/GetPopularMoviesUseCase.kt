package com.luis.android.themoviechallenge.domain.usecase

import com.luis.android.themoviechallenge.data.model.MovieResult
import com.luis.android.themoviechallenge.data.repository.MovieRepositoryImp
import com.luis.android.themoviechallenge.domain.helper.ResultWrapper
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(private val movieRepositoryImp: MovieRepositoryImp) {


    suspend fun getPopularMoviesByPage(pageNumber: String): ResultWrapper<MovieResult> {
        return movieRepositoryImp.getRemotePopularMovies(pageNumber)
    }

}