package com.itis.example.presentation.fragment.list

import androidx.lifecycle.*
import com.itis.example.R
import com.itis.example.di.ResourceProvider
import com.itis.example.domain.location.GetLocationUseCase
import com.itis.example.domain.location.model.LocationModel
import com.itis.example.domain.weather.GetWeatherByNameUseCase
import com.itis.example.domain.weather.GetWeatherListUseCase
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.presentation.fragment.utils.SingleLiveEvent
import com.itis.example.presentation.recycler.WeatherAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getWeatherByNameUseCase: GetWeatherByNameUseCase,
    private val getWeatherListUseCase: GetWeatherListUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val androidResourceProvider: ResourceProvider
) : ViewModel() {

    private val _adapter = MutableLiveData<WeatherAdapter>(null)
    val adapter: LiveData<WeatherAdapter>
        get() = _adapter

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _perms = MutableLiveData(false)
    val perms: LiveData<Boolean>
        get() = _perms

    private val _makeSnackBar = MutableLiveData<String?>(null)
    val makeSnackBar: LiveData<String?>
        get() = _makeSnackBar

    private val _shouldShowRationale = MutableLiveData(false)
    val shouldShowRationale: LiveData<Boolean>
        get() = _shouldShowRationale

    private val _location = MutableLiveData<LocationModel>(null)
    val location: LiveData<LocationModel>
        get() = _location

    private val _error = MutableLiveData<Throwable?>(null)
    val error: LiveData<Throwable?>
        get() = _error

    private val _weatherUIList = MutableLiveData<List<WeatherUIModel>>(null)
    val weatherUIList: LiveData<List<WeatherUIModel>>
        get() = _weatherUIList

    val navigateToDetails: SingleLiveEvent<Int> by lazy {
        return@lazy SingleLiveEvent<Int>()
    }

    fun onLocationPermsIsGranted(isGranted: Boolean) {
        _perms.value = isGranted
        onNeedList()
    }

    private fun onShouldNavigate(id: Int) {
        navigateToDetails.value = id
    }

    fun onNeedAdapter() {
        _adapter.value = WeatherAdapter {
            onShouldNavigate(it.id)
        }
    }

    fun onNeedLocation() {
        viewModelScope.launch {
            getLocationUseCase().let {
                Timber.e(it.toString())
                when {
                    it == null -> {
                        if (location.value == LocationModel(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)) {
                            onNeedList()
                            return@launch
                        }
                        _location.value = LocationModel(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                        _makeSnackBar.value =
                            androidResourceProvider.getString(R.string.location_not_found)
                    }
                    perms.value == false -> {
                        if (location.value == LocationModel(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)) {
                            onNeedList()
                            return@launch
                        }
                        _location.value = LocationModel(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
                        _shouldShowRationale.value = true
                    }
                    else -> {
                        _location.value = it
                    }
                }
            }

        }
    }

    fun onNeedList() {
        location.value?.run {
            getWeatherList(latitude, longitude)
        }
    }

    private fun getWeatherList(
        lat: Double,
        lon: Double,
        countOfCity: Int = DEFAULT_COUNT
    ) {
        viewModelScope.launch {
            try {
                Timber.e("$lat, $lon")
                _loading.value = true
                getWeatherListUseCase(lat, lon, countOfCity).let {
                    Timber.e(it.toString())
                    _weatherUIList.value = it
                }
            } catch (e: Throwable) {
                _error.value = e
            } finally {
                _loading.value = false
            }
        }
    }


    fun onLoadClick(query: String) {
        loadWeather(query)
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                getWeatherByNameUseCase(city).let {
                    it.apply {
                        onShouldNavigate(id)
                    }
                }
            } catch (error: Throwable) {
                _error.value = error
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        private const val DEFAULT_COUNT = 10
        private const val DEFAULT_LATITUDE = 55.75
        private const val DEFAULT_LONGITUDE = 37.61
    }
}
