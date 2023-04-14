package com.itis.example.di

import android.content.Context
import com.itis.example.App
import com.itis.example.presentation.FeatureModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        WeatherModule::class,
        AppModule::class,
        LocationModule::class,
        ViewModelModule::class,
        FeatureModule::class]
)
@Singleton
interface AppComponent {

    fun provideContext(): Context

    fun provideResourceProvider(): ResourceProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent

    }

    fun inject(application: App)
}
