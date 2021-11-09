package com.android.code.challenge.justo.data.api

import com.luis.android.themoviechallenge.data.model.MovieResult

interface MovieDbServiceHelper {

    suspend fun getPopularMovies(params : Map<String, String>): MovieResult

}