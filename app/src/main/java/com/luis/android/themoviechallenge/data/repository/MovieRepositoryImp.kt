package com.luis.android.themoviechallenge.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.android.code.challenge.justo.data.retrofit.MovieDbService
import com.luis.android.themoviechallenge.data.db.entity.PopularMoviesEntity
import com.luis.android.themoviechallenge.data.helpers.PopularMoviesEntityToPopularMovies
import com.luis.android.themoviechallenge.data.model.MovieResult
import com.luis.android.themoviechallenge.domain.helper.ResultWrapper
import com.luis.android.themoviechallenge.domain.model.PopularMovies
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class MovieRepositoryImp @Inject constructor(
    private val movieDbService: MovieDbService,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
    ) : MovieRepository {

    private val popularMoviesEntityToPopularMovies = PopularMoviesEntityToPopularMovies()

    override suspend fun getRemotePopularMovies(pageNumber:String): ResultWrapper<MovieResult> {
        return remoteDataSource.getUserProfileResponse(Dispatchers.IO) {movieDbService.getPopularMovies(pageNumber)}
    }

    override suspend fun insertMoviesDb(moviesList: MovieResult) {
        val moviesEntityList = mutableListOf<PopularMoviesEntity>()
        moviesList.results.map {
            moviesEntityList.add(PopularMoviesEntity(it.id,it.original_title,it.poster_path))
        }
        return localDataSource.insertMovies(moviesEntityList)
    }

    override fun getLocalPopularMovies(): LiveData<List<PopularMovies>> {
        return localDataSource.getMoviesList().map(popularMoviesEntityToPopularMovies::map)
    }

    override fun uploadImage(imagePath: String) {
        remoteDataSource.uploadImageFile(imagePath)
    }


}
