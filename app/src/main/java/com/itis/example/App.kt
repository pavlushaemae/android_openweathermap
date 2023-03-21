package com.itis.example

import android.app.Application
import com.itis.example.di.DataContainer
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DataContainer.provideLocationDataSource(this)
        DataContainer.provideResources(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
