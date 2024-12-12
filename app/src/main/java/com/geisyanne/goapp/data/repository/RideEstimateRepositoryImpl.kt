package com.geisyanne.goapp.data.repository

import com.geisyanne.goapp.data.api.ApiService
import com.geisyanne.goapp.data.mapper.toDomain
import com.geisyanne.goapp.data.request.RideEstimateRequest
import com.geisyanne.goapp.data.response.RideEstimateResponse
import com.geisyanne.goapp.domain.model.RideEstimateModel
import com.geisyanne.goapp.domain.repository.RideEstimateRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response

class RideEstimateRepositoryImpl(
    private val apiService: ApiService
) : RideEstimateRepository {

    override suspend fun getRideEstimate(request: RideEstimateRequest): Result<RideEstimateModel> {
        return try {
            val response = apiService.getRideEstimate(request)

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

    private fun handleHttpError(response: Response<RideEstimateResponse>): String {
        return try {
            if (response.code() == 400) {
                val errorJson = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorJson, ErrorResponse::class.java)

                val errorMessage = "Código de erro: ${errorResponse.error_code}, Descrição: ${errorResponse.error_description}"
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
