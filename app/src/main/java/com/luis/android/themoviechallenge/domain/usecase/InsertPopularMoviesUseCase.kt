package com.luis.android.themoviechallenge.domain.usecase

import com.luis.android.themoviechallenge.data.model.MovieResult
import com.luis.android.themoviechallenge.data.repository.MovieRepositoryImp
import javax.inject.Inject

class InsertPopularMoviesUseCase @Inject constructor(private val movieRepositoryImp: MovieRepositoryImp) {

    suspend fun insertPopularMovies(moviesList: MovieResult) {
        movieRepositoryImp.insertMoviesDb(moviesList)
    }

}