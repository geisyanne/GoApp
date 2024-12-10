package com.geisyanne.goapp.data.repository

import com.geisyanne.goapp.data.api.ApiService
import com.geisyanne.goapp.data.mapper.toDomain
import com.geisyanne.goapp.data.request.RideEstimateRequest
import com.geisyanne.goapp.domain.model.RideEstimateModel
import com.geisyanne.goapp.domain.repository.RideEstimateRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import retrofit2.HttpException

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
                    Result.failure(IllegalStateException())
                }
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Result.failure(e)
        }
    }
}
