package com.luis.android.themoviechallenge.di

import android.app.Application
import android.content.Context
import com.android.code.challenge.justo.data.api.MovieDbServiceHelper
import com.android.code.challenge.justo.data.api.MovieDbServiceImpl
import com.android.code.challenge.justo.data.retrofit.MovieDbService
import com.luis.android.themoviechallenge.data.api.AuthInterceptor
import com.luis.android.themoviechallenge.data.db.MoviesDb
import com.luis.android.themoviechallenge.data.db.dao.MoviesDao
import com.luis.android.themoviechallenge.data.helpers.StringDataCommonHelper.Companion.BASE_URL
import com.luis.android.themoviechallenge.data.repository.LocalDataSource
import com.luis.android.themoviechallenge.data.repository.MovieRepositoryImp
import com.luis.android.themoviechallenge.data.repository.RemoteDataSource
import com.luis.android.themoviechallenge.domain.usecase.GetLocalPopularMoviesUseCase
import com.luis.android.themoviechallenge.domain.usecase.GetPopularMoviesUseCase
import com.luis.android.themoviechallenge.domain.usecase.InsertPopularMoviesUseCase
import com.luis.android.themoviechallenge.domain.usecase.UploadImageUseCase
import com.lyft.android.themoviedbtest.view.fragment.LocationFragment
import com.lyft.android.themoviedbtest.view.fragment.MoviesFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MovieDbModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App{
        return app as App
    }

    @Provides
    fun provideBaseUrl() = BASE_URL


    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL : String) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) = retrofit.create(MovieDbService::class.java)

    @Singleton
    @Provides
    fun provideMovieServiceHelper(movieDbImpl: MovieDbServiceImpl): MovieDbServiceHelper = movieDbImpl

    @Singleton
    @Provides
    fun provideRemoteDataSource() = RemoteDataSource()

    @Singleton
    @Provides
    fun provideRepository(
        movieDbServiceHelper: MovieDbServiceHelper,
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource) = MovieRepositoryImp(movieDbServiceHelper, remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideGetPopularMoviesUseCase(movieRepositoryImp: MovieRepositoryImp) = GetPopularMoviesUseCase(movieRepositoryImp)

    @Singleton
    @Provides
    fun provideInsertPopularMoviesUseCase(movieRepositoryImp: MovieRepositoryImp) = InsertPopularMoviesUseCase(movieRepositoryImp)

    @Singleton
    @Provides
    fun provideGetLocalPopularMoviesUseCase(movieRepositoryImp: MovieRepositoryImp) = GetLocalPopularMoviesUseCase(movieRepositoryImp)

    @Singleton
    @Provides
    fun provideUploadImageUseCase(movieRepositoryImp: MovieRepositoryImp) = UploadImageUseCase(movieRepositoryImp)

    @Singleton
    @Provides
    fun provideMoviesDataBase(application:Application) = MoviesDb.getDatabaseClient(application)

    @Singleton
    @Provides
    fun provideMoviesDao(moviesDb:MoviesDb) = moviesDb.moviesDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(moviesDao:MoviesDao) = LocalDataSource(moviesDao)


}