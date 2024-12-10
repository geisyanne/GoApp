package co.geisyanne.goapp.data.api

import co.geisyanne.goapp.data.request.RideEstimateRequest
import co.geisyanne.goapp.data.response.RideEstimateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("ride/estimate")
    suspend fun getRideEstimate(
        @Body request: RideEstimateRequest,
    ): Response<RideEstimateResponse>

}