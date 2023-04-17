package com.itis.example.data.location.datasource

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.itis.example.domain.location.model.LocationModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocationDataSource @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    fun getLastLocation(): Single<LocationModel> {
        return Single.fromCallable {
            var locationModel: LocationModel? = null
            fusedLocationProviderClient.lastLocation.let {
                it.addOnSuccessListener { location ->
                    locationModel = LocationModel(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                }
                it.addOnCanceledListener {
                    return@addOnCanceledListener
                }
                return@fromCallable locationModel
            }
        }
    }
}
