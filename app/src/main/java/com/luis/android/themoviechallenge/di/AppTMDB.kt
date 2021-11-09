package com.luis.android.themoviechallenge.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppTMDB : Application(){

    override fun onCreate() {
        super.onCreate()
    }

}