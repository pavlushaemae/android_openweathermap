package com.itis.example.presentation.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.example.domain.weather.model.WeatherUIModel
import com.itis.example.presentation.recycler.utils.ItemCallback

class WeatherAdapter(
    private val action: (WeatherUIModel) -> Unit,
) : ListAdapter<WeatherUIModel, RecyclerView.ViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        WeatherHolder.create(parent, action)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as WeatherHolder).onBind(getItem(position))
}
