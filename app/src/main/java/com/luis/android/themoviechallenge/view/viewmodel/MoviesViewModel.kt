package com.luis.android.themoviechallenge.view.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.android.themoviechallenge.data.model.MovieResult
import com.luis.android.themoviechallenge.data.model.Result
import com.luis.android.themoviechallenge.domain.helper.ResultWrapper
import com.luis.android.themoviechallenge.domain.model.DomainMovieResult
import com.luis.android.themoviechallenge.domain.model.PopularMovies
import com.luis.android.themoviechallenge.domain.usecase.GetLocalPopularMoviesUseCase
import com.luis.android.themoviechallenge.domain.usecase.GetPopularMoviesUseCase
import com.luis.android.themoviechallenge.domain.usecase.InsertPopularMoviesUseCase
import kotlinx.coroutines.launch

class MoviesViewModel @ViewModelInject constructor(private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
                                                   private val insertPopularMoviesUseCase: InsertPopularMoviesUseCase,
                                                   private val getLocalPopularMoviesUseCase: GetLocalPopularMoviesUseCase
                                                   ) : ViewModel() {

    val popularMoviesResult = MutableLiveData<List<DomainMovieResult>>()
    val popularMoviesError = MutableLiveData<String>()


    fun getPopularMoviesRequest(pageNumber:String){
        viewModelScope.launch {
            when (val response = getPopularMoviesUseCase.getPopularMoviesByPage(pageNumber)) {
                is ResultWrapper.Success -> {
                    insertPopularMoviesUseCase.insertPopularMovies(response.value)
                    popularMoviesResult.postValue(responseToMoviesResult(response.value))
                }
                is ResultWrapper.RequestError -> popularMoviesError.postValue(response.code.toString() + " " + response.error)

            }
        }


    }

    fun getLocalPopularMovie(): LiveData<List<PopularMovies>> {
        return getLocalPopularMoviesUseCase.getPopularMoviesByPage()
    }

    private fun responseToMoviesResult(moviesResult : MovieResult):List<DomainMovieResult> {
        val movieResultList = mutableListOf<DomainMovieResult>()
        moviesResult.results.let {movieResult ->
            movieResultList.add(DomainMovieResult(getResultsList(movieResult),moviesResult.total_pages, moviesResult.page))
        }
        return movieResultList
    }

    private fun getResultsList(movieResult: List<Result>): List<PopularMovies> {
        val resultList = mutableListOf<PopularMovies>()
        movieResult.map {
            resultList.add(PopularMovies(it.id,it.original_title,it.poster_path))
        }

        return resultList
    }


}