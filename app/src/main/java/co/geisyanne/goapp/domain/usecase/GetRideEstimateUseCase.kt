package co.geisyanne.goapp.domain.usecase

import co.geisyanne.goapp.data.request.RideEstimateRequest
import co.geisyanne.goapp.domain.model.RideEstimateModel
import co.geisyanne.goapp.domain.repository.RideEstimateRepository

class GetRideEstimateUseCase(
    private val rideEstimateRepository: RideEstimateRepository
) {
    suspend operator fun invoke(request: RideEstimateRequest): Result<RideEstimateModel> {
        return try {
            val rideEstimate = rideEstimateRepository.getRideEstimate(request)
            Result.success(rideEstimate)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}