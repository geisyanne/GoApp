package com.geisyanne.goapp.domain.usecase

import com.geisyanne.goapp.data.request.Driver
import com.geisyanne.goapp.data.request.RideConfirmRequest
import com.geisyanne.goapp.domain.repository.RideConfirmRepository
import retrofit2.HttpException
import java.io.IOException

class GetRideConfirmUseCase(
    private val rideEstimateRepository: RideConfirmRepository
) {

    suspend operator fun invoke(
        customerId: String?,
        origin: String?,
        destination: String?,
        distance: Int?,
        duration: Int?,
        rideOptionId: Int?,
        rideOptionName: String?,
        rideOptionValue: Double?
    ): RideConfirmResult {
        return try {
            val request = RideConfirmRequest(
                customerId = if (customerId?.trim()?.isEmpty() == true) null else customerId,
                origin = if (origin?.trim()?.isEmpty() == true) null else origin,
                destination = if (destination?.trim()?.isEmpty() == true) null else destination,
                distance = distance,
                duration = duration?.toString(),
                driver = Driver(
                    id = rideOptionId,
                    name = rideOptionName
                ),
                value = rideOptionValue
            )

            val result = rideEstimateRepository.getRideConfirm(request)

            if (result.isSuccess) {
                RideConfirmResult.Success("Confirmação de viagem realizada com sucesso!")
            } else {
                val exception = result.exceptionOrNull()
                handleError(exception, exception?.message)
            }

        } catch (e: Exception) {
            RideConfirmResult.Failure("Erro genérico ao confirmar a viagem.")
        }
    }

    private fun handleError(
        exception: Throwable?,
        msg: String? = null
    ): RideConfirmResult.Failure {
        return when (exception) {
            is HttpException -> RideConfirmResult.Failure("Erro no servidor.")
            is IOException -> RideConfirmResult.Failure("Erro de rede.")
            is IllegalStateException -> RideConfirmResult.Failure(msg)
            else -> RideConfirmResult.Failure("Erro genérico ao confirmar a viagem.")
        }
    }
}

sealed class RideConfirmResult {
    data class Success(val rideConfirm: String) : RideConfirmResult()
    data class Failure(val resId: String?) : RideConfirmResult()
}
