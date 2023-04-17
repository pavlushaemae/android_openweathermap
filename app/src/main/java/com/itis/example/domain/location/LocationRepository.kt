package com.itis.example.domain.location

import com.itis.example.domain.location.model.LocationModel
import io.reactivex.rxjava3.core.Single

interface LocationRepository {
    fun getLocation(): Single<LocationModel>
}
