package com.geisyanne.goapp.data.request

import com.google.gson.annotations.SerializedName

data class RideConfirmRequest(
    @SerializedName("customer_id") var customerId: String? = null,
    var origin: String? = null,
    var destination: String? = null,
    var distance: Int? = null,
    var duration: String? = null,
    var driver: Driver? = null,
    var value: Double? = null
)

data class Driver(
    var id: Int? = null,
    var name: String? = null
)