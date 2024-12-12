package com.geisyanne.goapp.domain.usecase

import com.geisyanne.goapp.R
import com.geisyanne.goapp.data.request.RideEstimateRequest
import com.geisyanne.goapp.domain.model.RideEstimateModel
import com.geisyanne.goapp.domain.repository.RideEstimateRepository
import retrofit2.HttpException
import java.io.IOException

class GetRideEstimateUseCase(
    private val rideEstimateRepository: RideEstimateRepository
) {

    suspend operator fun invoke(
        customerId: String?,
        origin: String?,
        destination: String?
    ): RideEstimateResult {

        return try {
            val request = RideEstimateRequest(
                customerId = if (customerId?.trim()?.isEmpty() == true) null else customerId,
                origin = if (origin?.trim()?.isEmpty() == true) null else origin,
                destination = if (destination?.trim()?.isEmpty() == true) null else destination
            )
            val result = rideEstimateRepository.getRideEstimate(request)

            if (result.isSuccess) {
                val rideEstimate = result.getOrNull()
                if (rideEstimate != null) {
                    RideEstimateResult.Success(rideEstimate)
                } else {
                    RideEstimateResult.Failure(R.string.error_generic.toString())
                }
            } else {
                val exception = result.exceptionOrNull()

                handleError(exception, exception?.message)
            }

        } catch (e: Exception) {
            RideEstimateResult.Failure(R.string.error_generic.toString())
        }
    }

    private fun handleError(
        exception: Throwable?,
        msg: String? = null
    ): RideEstimateResult.Failure {
        return when (exception) {
            is HttpException -> RideEstimateResult.Failure(R.string.error_server.toString())
            is IOException -> RideEstimateResult.Failure(R.string.error_network.toString())
            is IllegalStateException -> RideEstimateResult.Failure(msg)
            else -> RideEstimateResult.Failure(R.string.error_generic.toString())
        }
    }

}

sealed class RideEstimateResult {
    data class Success(val rideEstimate: RideEstimateModel) : RideEstimateResult()
    data class Failure(val resId: String?) : RideEstimateResult()
}