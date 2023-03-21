package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo

class GetWeatherByNameUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        name: String
    ): WeatherInfo = weatherRepository.getWeatherByName(name)
}
