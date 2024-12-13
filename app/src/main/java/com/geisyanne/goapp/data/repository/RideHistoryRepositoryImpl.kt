package com.geisyanne.goapp.data.repository

import com.geisyanne.goapp.data.api.ApiService
import com.geisyanne.goapp.data.mapper.toDomain
import com.geisyanne.goapp.data.response.RideHistoryResponse
import com.geisyanne.goapp.domain.model.RideHistoryModel
import com.geisyanne.goapp.domain.repository.RideHistoryRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import retrofit2.Response

class RideHistoryRepositoryImpl(
    private val apiService: ApiService
) : RideHistoryRepository {

    override suspend fun getRideHistory(
        customerId: String,
        driverId: Int?
    ): Result<RideHistoryModel> {

        return try {
            val response = apiService.getRideHistory(customerId, driverId)

            if (response.isSuccessful) {
                val body = response.body()?.toDomain()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(IllegalStateException("Resposta da API está vazia"))
                }
            } else {
                val errorMessage = handleHttpError(response)
                Result.failure(IllegalStateException(errorMessage))
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Result.failure(e)
        }
    }

    private fun handleHttpError(response: Response<RideHistoryResponse>): String {
        return try {
            if (response.code() == 400 || response.code() == 404) {
                val errorJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

                val errorMessage =
                    "Código de erro: ${errorResponse.error_code}, Descrição: ${errorResponse.error_description}"
                FirebaseCrashlytics.getInstance().log(errorMessage)

                errorResponse.error_description
            } else {
                "Erro na API: Status Code ${response.code()}"
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            "Erro ao processar a resposta: ${e.message}"
        }
    }

    data class ErrorResponse(
        val error_code: String,
        val error_description: String
    )

}