package com.luis.android.themoviechallenge.di

import android.app.Application
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object MovieDbModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL : String) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideMovieService(retrofit: Retrofit):MovieDbService = retrofit.create(MovieDbService::class.java)

    @Provides
    fun provideRemoteDataSource() = RemoteDataSource()

    @Provides
    fun provideRepository(
        movieDbService: MovieDbService,
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource) = MovieRepositoryImp(movieDbService, remoteDataSource, localDataSource)

    @Provides
    fun provideGetPopularMoviesUseCase(movieRepositoryImp: MovieRepositoryImp) = GetPopularMoviesUseCase(movieRepositoryImp)

    @Provides
    fun provideInsertPopularMoviesUseCase(movieRepositoryImp: MovieRepositoryImp) = InsertPopularMoviesUseCase(movieRepositoryImp)

    @Provides
    fun provideGetLocalPopularMoviesUseCase(movieRepositoryImp: MovieRepositoryImp) = GetLocalPopularMoviesUseCase(movieRepositoryImp)

    @Provides
    fun provideUploadImageUseCase(movieRepositoryImp: MovieRepositoryImp) = UploadImageUseCase(movieRepositoryImp)

    @Provides
    fun provideMoviesDataBase(application:Application) = MoviesDb.getDatabaseClient(application)

    @Provides
    fun provideMoviesDao(moviesDb:MoviesDb) = moviesDb.moviesDao()

    @Provides
    fun provideLocalDataSource(moviesDao:MoviesDao) = LocalDataSource(moviesDao)


}