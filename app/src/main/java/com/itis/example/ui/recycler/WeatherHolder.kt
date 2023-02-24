package com.itis.example.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.example.data.response.WeatherResponse
import com.itis.example.databinding.ItemWeatherBinding

class WeatherHolder (
    private val binding: ItemWeatherBinding,
    private val action: (WeatherResponse) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var weather: WeatherResponse? = null

        init {
            binding.root.setOnClickListener {
                weather?.also(action)
            }
        }

        fun onBind(weather: WeatherResponse) {
            this.weather = weather
            with(binding) {

            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                action: (WeatherResponse) -> Unit,
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