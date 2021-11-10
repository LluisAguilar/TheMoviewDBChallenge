package com.android.code.challenge.justo.data.retrofit


import com.luis.android.themoviechallenge.data.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {

    @GET("/3/movie/popular?language=es-MX")
    suspend fun getPopularMovies(@Query("page") page:String): MovieResult
}