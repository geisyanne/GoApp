package com.geisyanne.goapp.domain.usecase

import com.geisyanne.goapp.R
import com.geisyanne.goapp.domain.model.RideHistoryModel
import com.geisyanne.goapp.domain.repository.RideHistoryRepository
import retrofit2.HttpException
import java.io.IOException

class GetRideHistoryUseCase(
    private val rideHistoryRepository: RideHistoryRepository
) {

    suspend operator fun invoke(
        customerId: String,
        driverId: Int?,
    ): RideHistoryResult {

        return try {
            val result = rideHistoryRepository.getRideHistory(customerId, driverId)

            if (result.isSuccess) {
                val rideHistory = result.getOrNull()
                if (rideHistory != null) {
                    RideHistoryResult.Success(rideHistory)
                } else {
                    RideHistoryResult.Failure(R.string.error_generic.toString())
                }
            } else {
                val exception = result.exceptionOrNull()

                handleError(exception, exception?.message)
            }

        } catch (e: Exception) {
            RideHistoryResult.Failure(R.string.error_generic.toString())
        }
    }

    private fun handleError(
        exception: Throwable?,
        msg: String? = null
    ): RideHistoryResult.Failure {
        return when (exception) {
            is HttpException -> RideHistoryResult.Failure(R.string.error_server.toString())
            is IOException -> RideHistoryResult.Failure(R.string.error_network.toString())
            is IllegalStateException -> RideHistoryResult.Failure(msg)
            else -> RideHistoryResult.Failure(R.string.error_generic.toString())
        }
    }

}

sealed class RideHistoryResult {
    data class Success(val rideHistory: RideHistoryModel) : RideHistoryResult()
    data class Failure(val resId: String?) : RideHistoryResult()
}