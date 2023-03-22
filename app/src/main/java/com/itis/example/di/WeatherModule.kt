package com.itis.example.di

import com.itis.example.data.weather.WeatherRepositoryImpl
import com.itis.example.data.weather.datasource.remote.WeatherApi
import com.itis.example.domain.weather.WeatherRepository
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {

    @Provides
    fun provideWeatherRepository(
        weatherApi: WeatherApi
    ): WeatherRepository = WeatherRepositoryImpl(weatherApi)
}
