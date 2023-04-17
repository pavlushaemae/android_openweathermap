package com.itis.example.domain.weather

import com.itis.example.domain.weather.model.WeatherUIModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetWeatherListUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        lat: Double,
        lon: Double,
        cnt: Int
    ): Single<List<WeatherUIModel>> = weatherRepository.getSomeCities(lat, lon, cnt)
}
