package co.geisyanne.goapp.domain.repository

import co.geisyanne.goapp.data.request.RideEstimateRequest
import co.geisyanne.goapp.domain.model.RideEstimateModel

interface RideEstimateRepository {

    suspend fun getRideEstimate(request: RideEstimateRequest) : RideEstimateModel

}