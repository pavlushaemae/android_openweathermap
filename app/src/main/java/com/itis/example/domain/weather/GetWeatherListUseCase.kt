package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherUIModel

class GetWeatherListUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double,
        cnt: Int
    ): List<WeatherUIModel> = weatherRepository.getSomeCities(lat, lon, cnt)
}
