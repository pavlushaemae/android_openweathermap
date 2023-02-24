package com.itis.example.ui.recycler.utils

import androidx.recyclerview.widget.DiffUtil
import com.itis.example.data.response.WeatherResponse

object ItemCallback : DiffUtil.ItemCallback<WeatherResponse>() {
    override fun areItemsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: WeatherResponse, newItem: WeatherResponse): Boolean = oldItem == newItem
}