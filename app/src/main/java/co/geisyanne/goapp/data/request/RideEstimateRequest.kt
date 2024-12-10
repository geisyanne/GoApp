package co.geisyanne.goapp.data.request

import com.google.gson.annotations.SerializedName

data class RideEstimateRequest(
    @SerializedName("customer_id") var id: String? = null,
    @SerializedName("origin") var origin: String? = null,
    @SerializedName("destination") var destination: String? = null
)