package com.luis.android.themoviechallenge.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.luis.android.themoviechallenge.data.db.entity.PopularMoviesEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(popularMoviesEntity: PopularMoviesEntity)

    @Query("SELECT * FROM PopularMovies")
    fun getMovies() : LiveData<List<PopularMoviesEntity>>

}