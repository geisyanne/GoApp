package com.geisyanne.goapp.data.api

import com.geisyanne.goapp.data.request.RideConfirmRequest
import com.geisyanne.goapp.data.request.RideEstimateRequest
import com.geisyanne.goapp.data.response.RideConfirmResponse
import com.geisyanne.goapp.data.response.RideEstimateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @POST("ride/estimate")
    suspend fun getRideEstimate(
        @Body request: RideEstimateRequest,
    ): Response<RideEstimateResponse>

    @PATCH("ride/confirm")
    suspend fun getConfirmRide(
        @Body request: RideConfirmRequest,
    ): Response<RideConfirmResponse>

}