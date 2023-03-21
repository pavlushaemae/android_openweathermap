package com.itis.example.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.databinding.ItemWeatherBinding

class WeatherHolder(
    private val binding: ItemWeatherBinding,
    private val action: (WeatherUIModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var weather: WeatherUIModel? = null

    init {
        binding.root.setOnClickListener {
            weather?.also(action)
        }
    }

    fun onBind(weather: WeatherUIModel) {
        this.weather = weather
        with(binding) {
            tvName.text = weather.name
            tvTemp.text = weather.temp
            tvTemp.setTextColor(binding.root.context.getColor(weather.tempColor))
            ivIcon.load("https://openweathermap.org/img/w/${weather.icon}.png") {
                crossfade(true)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            action: (WeatherUIModel) -> Unit,
        ): WeatherHolder = WeatherHolder(
            binding = ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            action = action,
        )
    }
}
