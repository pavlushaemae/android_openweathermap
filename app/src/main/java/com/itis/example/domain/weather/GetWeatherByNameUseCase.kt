package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherInfo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWeatherByNameUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        name: String
    ): Single<WeatherInfo> = weatherRepository.getWeatherByName(name)
}
