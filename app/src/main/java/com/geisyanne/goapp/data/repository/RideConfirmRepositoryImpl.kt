package com.geisyanne.goapp.data.repository

import com.geisyanne.goapp.data.api.ApiService
import com.geisyanne.goapp.data.repository.RideEstimateRepositoryImpl.ErrorResponse
import com.geisyanne.goapp.data.request.RideConfirmRequest
import com.geisyanne.goapp.data.response.RideConfirmResponse
import com.geisyanne.goapp.domain.repository.RideConfirmRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import retrofit2.Response

class RideConfirmRepositoryImpl(
    private val apiService: ApiService
) : RideConfirmRepository {

    override suspend fun getRideConfirm(request: RideConfirmRequest): Result<Boolean> {
        return try {
            val response = apiService.getConfirmRide(request)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                val errorMessage = handleHttpError(response)
                Result.failure(IllegalStateException(errorMessage))
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Result.failure(e)
        }
    }

    private fun handleHttpError(response: Response<RideConfirmResponse>): String {
        return try {
            if (response.code() == 400  || response.code() == 404 || response.code() == 406) {
                val errorJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

                val errorMessage =
                    "Código de erro: ${errorResponse.error_code}, Descrição: ${errorResponse.error_description}"
                FirebaseCrashlytics.getInstance().log(errorMessage)

                errorResponse.error_description
            } else  {

                "Erro na API: Status Code ${response.code()}"
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            "Erro ao processar a resposta: ${e.message}"
        }
    }
}