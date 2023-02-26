package com.itis.example.ui.recycler.utils

import androidx.recyclerview.widget.DiffUtil
import com.itis.example.data.model.WeatherUIModel

object ItemCallback : DiffUtil.ItemCallback<WeatherUIModel>() {
    override fun areItemsTheSame(oldItem: WeatherUIModel, newItem: WeatherUIModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WeatherUIModel, newItem: WeatherUIModel): Boolean =
        oldItem == newItem
}
