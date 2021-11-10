package com.luis.android.themoviechallenge.data.repository

import androidx.lifecycle.LiveData
import com.luis.android.themoviechallenge.data.db.dao.MoviesDao
import com.luis.android.themoviechallenge.data.db.entity.PopularMoviesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val moviesDao: MoviesDao) {


    suspend fun insertMovies(moviesList: List<PopularMoviesEntity>) {
        moviesList.map {
            moviesDao.insertMovie(it)
        }
    }

    fun getMoviesList() : LiveData<List<PopularMoviesEntity>> {
        return moviesDao.getMovies()
    }


}