package com.itis.example.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.itis.example.R
import com.itis.example.data.response.WeatherResponse
import com.itis.example.data.service.WeatherService
import com.itis.example.data.service.impl.WeatherServiceImpl
import com.itis.example.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var weatherService: WeatherService? = null
    private var weather: WeatherResponse? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        weatherService = WeatherServiceImpl
        val id: Int = arguments?.run {
            getInt(ARG_ID)
        } ?: 0
        lifecycleScope.launch {
            weather = weatherService?.getWeatherById(id)
            fillViews()
        }

    }

    private fun fillViews() {
        binding.run {
            tvName.text = weather?.name
            loadIcon()
            tvHumidity.text = getString(R.string.humidity, weather?.main?.humidity.toString())
            tvTemp.text = getString(
                R.string.degree,
                weather?.main?.temp.toString(),
                weather?.weather?.firstOrNull()?.main.toString()
            )
            tvWind.text = degreeToString(weather?.wind?.deg)
            tvWindSpeed.text = getString(R.string.wind_speed, weather?.wind?.speed.toString())
            tvPressure.text = getString(R.string.pressure, weather?.main?.pressure.toString())
            tvTempFeel.text =
                getString(R.string.feels_like, weather?.main?.feelsLike.toString())
            tvSeaLevel.text = getString(R.string.sea_level, weather?.main?.seaLevel.toString())
            tvTempMax.text = getString(R.string.temp, weather?.main?.tempMax.toString())
            tvMinTemp.text = getString(R.string.temp, weather?.main?.tempMin.toString())
        }
    }

    private fun loadIcon() {
        weather?.weather?.firstOrNull()?.also {
            binding.ivIcon.load("https://openweathermap.org/img/w/${it.icon}.png") {
                crossfade(true)
            }
        }
    }

    private fun degreeToString(degree: Int?): String? {
        return context?.let {
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

    override fun onDestroyView() {
        super.onDestroyView()
        weatherService = null
        _binding = null
        weather = null
    }

    companion object {
        private const val ARG_ID = "arg_id"

        fun newInstance(idOfCity: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, idOfCity)
                }
            }
    }
}
