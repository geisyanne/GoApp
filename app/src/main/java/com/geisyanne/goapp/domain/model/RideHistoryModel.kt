package com.geisyanne.goapp.domain.model


data class RideHistoryModel(
    val customerId: String?,
    val rides: List<RideModel>?
)

data class RideModel(
    val id: Int?,
    val date: String?,
    val origin: String?,
    val destination: String?,
    val distance: Double?,
    val duration: String?,
    val driver: DriverModel?,
    val value: Double?
)

data class DriverModel(
    val id: Int?,
    val name: String?
)


