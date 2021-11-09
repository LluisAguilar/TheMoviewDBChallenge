package com.android.code.challenge.justo.data.api

import com.android.code.challenge.justo.data.retrofit.MovieDbService
import com.luis.android.themoviechallenge.data.model.MovieResult
import javax.inject.Inject

class MovieDbServiceImpl @Inject constructor(
    private val movieDbService: MovieDbService
): MovieDbServiceHelper {
    override suspend fun getPopularMovies(params: Map<String, String>): MovieResult = movieDbService.getPopularMovies(params)

}