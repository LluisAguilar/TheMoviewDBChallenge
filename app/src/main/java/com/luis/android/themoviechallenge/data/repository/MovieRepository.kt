package com.luis.android.themoviechallenge.data.repository

import androidx.lifecycle.LiveData
import com.luis.android.themoviechallenge.data.model.MovieResult
import com.luis.android.themoviechallenge.domain.helper.ResultWrapper
import com.luis.android.themoviechallenge.domain.model.PopularMovies

interface MovieRepository {

    suspend fun getRemotePopularMovies(pageNumber:String = "1") : ResultWrapper<MovieResult>
    suspend fun insertMoviesDb(moviesList: MovieResult)
    fun getLocalPopularMovies() : LiveData<List<PopularMovies>>
    fun uploadImage(imagePath:String)

}