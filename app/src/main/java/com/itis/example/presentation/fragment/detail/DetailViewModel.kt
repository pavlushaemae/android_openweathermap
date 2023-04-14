package com.itis.example.presentation.fragment.detail

import androidx.lifecycle.*
import com.itis.example.R
import com.itis.example.di.ResourceProvider
import com.itis.example.domain.weather.GetWeatherByIdUseCase
import com.itis.example.presentation.model.WeatherModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase,
    private val androidResourceProvider: ResourceProvider,
    @Assisted private val cityId: Int
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _weather = MutableLiveData<WeatherModel?>(null)
    val weather: LiveData<WeatherModel?>
        get() = _weather

    fun loadWeather() {
        viewModelScope.launch {
            try {
                _loading.value = true
                androidResourceProvider.let {
                    getWeatherByIdUseCase(cityId).run {

                        _weather.value = WeatherModel(
                            cityName = cityName,
                            humidity = it.getString(
                                R.string.humidity,
                                humidity.toString()
                            ),
                            minTemp = it.getString(R.string.temp, tempMin.toString()),
                            tempMax = it.getString(R.string.temp, tempMax.toString()),
                            pressure = it.getString(R.string.pressure, pressure.toString()),
                            tempFeel = it.getString(R.string.feels_like, feelsLike.toString()),
                            temperature = "${
                                it.getString(
                                    R.string.degree,
                                    temperature.toString()
                                )
                            } $main",
                            wind = degreeToString(windDeg),
                            windSpeed = it.getString(R.string.wind_speed, windSpeed.toString()),
                            seaLevel = it.getString(R.string.sea_level, seaLevel.toString()),
                            icon = "https://openweathermap.org/img/w/${icon}.png"
                        )
                    }
                }
            } catch (error: Throwable) {
                _error.value = error
            } finally {
                _loading.value = false
            }
        }
    }


    private fun degreeToString(degree: Int?): String? {
        return androidResourceProvider.let {
            when (degree) {
                in 0..22 -> it.getString(R.string.north)
                in 338..360 -> it.getString(R.string.north)
                in 23..67 -> it.getString(R.string.north_east)
                in 68..112 -> it.getString(R.string.east)
                in 113..157 -> it.getString(R.string.south_east)
                in 158..202 -> it.getString(R.string.south)
                in 203..247 -> it.getString(R.string.south_west)
                in 248..292 -> it.getString(R.string.west)
                in 293..337 -> it.getString(R.string.north_west)
                else -> null
            }
        }
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun newInstance(cityId: Int): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DetailViewModelFactory,
            cityId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.newInstance(cityId) as T
        }
    }
}
