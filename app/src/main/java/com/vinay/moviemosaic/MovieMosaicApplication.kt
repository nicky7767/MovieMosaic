package com.vinay.moviemosaic

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MovieMosaicApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}