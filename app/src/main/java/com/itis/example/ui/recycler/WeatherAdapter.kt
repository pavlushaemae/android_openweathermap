package com.itis.example.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.example.data.response.WeatherResponse
import com.itis.example.ui.recycler.utils.ItemCallback

class WeatherAdapter (
    private val action: (WeatherResponse) -> Unit,
    ) : ListAdapter<WeatherResponse, RecyclerView.ViewHolder>(ItemCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            WeatherHolder.create(parent, action)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            (holder as WeatherHolder).onBind(getItem(position))
}