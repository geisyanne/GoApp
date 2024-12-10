package com.geisyanne.goapp.domain.usecase

import android.util.Log
import com.geisyanne.goapp.R
import com.geisyanne.goapp.data.request.RideEstimateRequest
import com.geisyanne.goapp.domain.model.RideEstimateModel
import com.geisyanne.goapp.domain.repository.RideEstimateRepository
import retrofit2.HttpException
import java.io.IOException

class GetRideEstimateUseCase(
    private val rideEstimateRepository: RideEstimateRepository,
) {

    suspend operator fun invoke(
        customerId: String?,
        origin: String?,
        destination: String?
    ): RideEstimateResult {

        if (customerId.isNullOrBlank()) {
            return RideEstimateResult.Failure(R.string.error_customer_id_not_provided)
        }

        if (origin.isNullOrBlank()) {
            return RideEstimateResult.Failure(R.string.error_origin_not_provided)
        }

        if (destination.isNullOrBlank()) {
            return RideEstimateResult.Failure(R.string.error_destination_not_provided)
        }

        if (destination.trim().equals(origin.trim(), ignoreCase = true)) {
            return RideEstimateResult.Failure(R.string.error_destination_equals_origin)
        }

        return try {
            val request = RideEstimateRequest(
                customerId = customerId,
                origin = origin,
                destination = destination
            )
            val result = rideEstimateRepository.getRideEstimate(request)

            if (result.isSuccess) {
                val rideEstimate = result.getOrNull()
                if (rideEstimate != null) {
                    RideEstimateResult.Success(rideEstimate)
                } else {
                    RideEstimateResult.Failure(R.string.error_generic)
                }
            } else {
                val exception = result.exceptionOrNull()
                handleError(exception)
            }

        } catch (e: Exception) {
            Log.e("GetRideEstimateUseCase", "Failed to get ride estimate", e)
            RideEstimateResult.Failure(R.string.error_generic)
        }
    }

    private fun handleError(exception: Throwable?): RideEstimateResult.Failure {
        return when (exception) {
            is HttpException -> RideEstimateResult.Failure(R.string.error_server)
            is IOException -> RideEstimateResult.Failure(R.string.error_network)
            is IllegalStateException -> RideEstimateResult.Failure(R.string.error_invalid_response)
            else -> RideEstimateResult.Failure(R.string.error_generic)
        }
    }

}

sealed class RideEstimateResult {
    data class Success(val rideEstimate: RideEstimateModel) : RideEstimateResult()
    data class Failure(val resId: Int) : RideEstimateResult()
}