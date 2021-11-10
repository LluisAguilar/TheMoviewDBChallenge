package com.luis.android.themoviechallenge.domain.usecase

import androidx.lifecycle.LiveData
import com.luis.android.themoviechallenge.data.repository.MovieRepositoryImp
import com.luis.android.themoviechallenge.domain.model.PopularMovies
import javax.inject.Inject

class GetLocalPopularMoviesUseCase @Inject constructor(private val movieRepositoryImp: MovieRepositoryImp) {


    fun getPopularMoviesByPage(): LiveData<List<PopularMovies>> {
        return movieRepositoryImp.getLocalPopularMovies()
    }

}