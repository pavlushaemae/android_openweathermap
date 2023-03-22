package com.itis.example.di

import android.content.Context
import androidx.fragment.app.ListFragment
import com.itis.example.presentation.fragment.DetailFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [NetworkModule::class, WeatherModule::class, AppModule::class,
        LocationModule::class]
)
@Singleton
interface AppComponent {

    fun provideContext(): Context

    fun provideResourceProvider(): ResourceProvider

    fun inject(listFragment: ListFragment)

    fun inject(detailFragment: DetailFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent

    }
}
