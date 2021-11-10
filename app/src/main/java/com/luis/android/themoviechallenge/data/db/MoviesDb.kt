package com.luis.android.themoviechallenge.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luis.android.themoviechallenge.data.db.dao.MoviesDao
import com.luis.android.themoviechallenge.data.db.entity.PopularMoviesEntity
import com.luis.android.themoviechallenge.data.helpers.StringDataCommonHelper.Companion.MOVIES_ROOM_DATABASE_NAME

@Database(entities = arrayOf(PopularMoviesEntity::class), version = 1, exportSchema = false)
abstract class MoviesDb() : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao


    companion object {

        @Volatile
        private var mInstance: MoviesDb? = null

        fun getDatabaseClient(application: Application): MoviesDb {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = Room
                        .databaseBuilder(application, MoviesDb::class.java, MOVIES_ROOM_DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            } else {
                return mInstance!!
            }

            return mInstance!!
        }

    }
}