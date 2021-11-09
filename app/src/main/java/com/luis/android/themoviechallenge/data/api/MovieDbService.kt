package com.android.code.challenge.justo.data.retrofit


import com.luis.android.themoviechallenge.data.model.MovieResult
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface MovieDbService {

    @GET("/movie/popular?page=1&language=es-MX")
    @FormUrlEncoded
    suspend fun getPopularMovies(@FieldMap params : Map<String, String>): MovieResult
}