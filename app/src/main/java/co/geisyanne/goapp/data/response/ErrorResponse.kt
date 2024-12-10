package co.geisyanne.goapp.data.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error_code")
    val errorCode: String? = null,

    @SerializedName("error_description")
    val errorDescription: String? = null
)