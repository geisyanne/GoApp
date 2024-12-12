package com.geisyanne.goapp.data.response

import com.google.gson.annotations.SerializedName

data class RideConfirmResponse(
    @SerializedName("success") var success: Boolean? = null,
    @SerializedName("error_code") var errorCode: String? = null,
    @SerializedName("error_description") var errorDescription: String? = null
)