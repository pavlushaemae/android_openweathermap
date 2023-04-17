package com.itis.example.presentation.fragment.detail

import androidx.lifecycle.*
import com.itis.example.R
import com.itis.example.di.ResourceProvider
import com.itis.example.domain.weather.GetWeatherByIdUseCase
import com.itis.example.domain.weather.model.WeatherInfo
import com.itis.example.presentation.model.WeatherModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy

class DetailViewModel @AssistedInject constructor(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase,
    private val androidResourceProvider: ResourceProvider,
    @Assisted private val cityId: Int
) : ViewModel() {

    private var weatherDisposable: Disposable? = null

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
        weatherDisposable = getWeatherByIdUseCase(cityId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loading.value = true }
            .doAfterTerminate { _loading.value = false }
            .subscribeBy(
                onSuccess = { weatherInfo ->
                    _weather.value = weatherInfo.toWeatherModel()
                }, onError = { error ->
                    _error.value = error
                }
            )
    }

    private fun WeatherInfo.toWeatherModel(): WeatherModel {
        return androidResourceProvider.let { resourceProvider ->
            WeatherModel(
                cityName = cityName,
                humidity = resourceProvider.getString(
                    R.string.humidity,
                    humidity.toString()
                ),
                minTemp = resourceProvider.getString(
                    R.string.temp,
                    tempMin.toString()
                ),
                tempMax = resourceProvider.getString(
                    R.string.temp,
                    tempMax.toString()
                ),
                pressure = resourceProvider.getString(
                    R.string.pressure,
                    pressure.toString()
                ),
                tempFeel = resourceProvider.getString(
                    R.string.feels_like,
                    feelsLike.toString()
                ),
                temperature = "${
                    resourceProvider.getString(
                        R.string.degree,
                        temperature.toString()
                    )
                } $main",
                wind = degreeToString(windDeg),
                windSpeed = resourceProvider.getString(
                    R.string.wind_speed,
                    windSpeed.toString()
                ),
                seaLevel = resourceProvider.getString(
                    R.string.sea_level,
                    seaLevel.toString()
                ),
                icon = "https://openweathermap.org/img/w/${icon}.png"
            )
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

    override fun onCleared() {
        super.onCleared()
        weatherDisposable?.dispose()
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
