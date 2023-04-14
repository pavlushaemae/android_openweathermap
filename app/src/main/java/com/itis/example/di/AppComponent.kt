package com.itis.example.di

import android.content.Context
import com.itis.example.presentation.fragment.detail.DetailComponent
import com.itis.example.presentation.fragment.list.ListFragment
import com.itis.example.presentation.fragment.detail.DetailFragment
import com.itis.example.presentation.fragment.list.ListComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [NetworkModule::class,
        WeatherModule::class,
        AppModule::class,
        LocationModule::class,
        ViewModelModule::class]
)
@Singleton
interface AppComponent {

    fun provideContext(): Context

    fun provideResourceProvider(): ResourceProvider

    fun plusDetailComponent(): DetailComponent.Builder

    fun plusListComponent(): ListComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): AppComponent

    }
}
