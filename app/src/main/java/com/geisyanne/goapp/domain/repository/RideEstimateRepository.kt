package com.geisyanne.goapp.domain.repository

import com.geisyanne.goapp.data.request.RideEstimateRequest
import com.geisyanne.goapp.domain.model.RideEstimateModel

interface RideEstimateRepository {

    suspend fun getRideEstimate(request: RideEstimateRequest) : Result<RideEstimateModel>

}