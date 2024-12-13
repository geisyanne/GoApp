package com.geisyanne.goapp.data.response

import com.google.gson.annotations.SerializedName

data class RideHistoryResponse(
    @SerializedName("customer_id") val customerId: String? = null,
    val rides: List<RideResponse>? = null
)

data class RideResponse(
    val id: Int? = null,
    val date: String? = null,
    val origin: String? = null,
    val destination: String? = null,
    val distance: Double? = null,
    val duration: String? = null,
    val driver: DriverResponse? = null,
    val value: Double? = null
)

data class DriverResponse(
    val id: Int? = null,
    val name: String? = null
)
