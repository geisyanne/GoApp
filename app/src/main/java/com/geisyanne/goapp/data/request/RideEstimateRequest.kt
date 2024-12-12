package com.geisyanne.goapp.data.request

import com.google.gson.annotations.SerializedName

data class RideEstimateRequest(
    @SerializedName("customer_id") var customerId: String? = null,
    var origin: String? = null,
    var destination: String? = null
)