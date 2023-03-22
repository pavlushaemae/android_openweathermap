package com.itis.example

import android.app.Application
import com.itis.example.di.AppComponent
import com.itis.example.di.DaggerAppComponent
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }
    companion object {
        lateinit var appComponent: AppComponent
    }
}
