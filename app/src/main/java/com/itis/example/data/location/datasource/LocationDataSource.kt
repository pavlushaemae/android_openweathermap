package com.itis.example.data.location.datasource

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.itis.example.domain.location.model.LocationModel
import kotlinx.coroutines.tasks.await

class LocationDataSource(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): LocationModel? = fusedLocationProviderClient.lastLocation.await().let {
            if (it != null) {
                LocationModel(
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            } else {
                null
            }
        }
}
