package com.luis.android.themoviechallenge.di

import com.android.code.challenge.justo.data.api.MovieDbServiceHelper
import com.android.code.challenge.justo.data.api.MovieDbServiceImpl
import com.android.code.challenge.justo.data.retrofit.MovieDbService
import com.luis.android.themoviechallenge.data.api.AuthInterceptor
import com.luis.android.themoviechallenge.data.helpers.StringDataCommonHelper.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object MovieDbModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideInterceptor() = AuthInterceptor()


    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
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
    fun provideJustoService(retrofit: Retrofit) = retrofit.create(MovieDbService::class.java)

    @Singleton
    @Provides
    fun provideJustoServiceHelper(movieDbImpl: MovieDbServiceImpl): MovieDbServiceHelper = movieDbImpl



}