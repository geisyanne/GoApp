package com.geisyanne.goapp.data.mapper

import com.geisyanne.goapp.data.response.DriverResponse
import com.geisyanne.goapp.data.response.RideHistoryResponse
import com.geisyanne.goapp.data.response.RideResponse
import com.geisyanne.goapp.domain.model.DriverModel
import com.geisyanne.goapp.domain.model.RideHistoryModel
import com.geisyanne.goapp.domain.model.RideModel

fun RideHistoryResponse.toDomain(): RideHistoryModel {
    return RideHistoryModel(
        customerId = this.customerId,
        rides = this.rides?.map { it.toDomain() }
    )
}

fun RideResponse.toDomain(): RideModel {
    return RideModel(
        id = this.id,
        date = this.date,
        origin = this.origin,
        destination = this.destination,
        distance = this.distance,
        duration = this.duration,
        driver = this.driver?.toDomain(),
        value = this.value
    )
}

fun DriverResponse.toDomain(): DriverModel {
    return DriverModel(
        id = this.id,
        name = this.name
    )
}