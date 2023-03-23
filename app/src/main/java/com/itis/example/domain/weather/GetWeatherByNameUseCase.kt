package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import javax.inject.Inject

class GetWeatherByNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        name: String
    ): WeatherInfo = weatherRepository.getWeatherByName(name)
}
