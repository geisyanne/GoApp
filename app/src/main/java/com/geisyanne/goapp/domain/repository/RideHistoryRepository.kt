package com.geisyanne.goapp.domain.repository

import com.geisyanne.goapp.domain.model.RideHistoryModel

interface RideHistoryRepository {

    suspend fun getRideHistory(customerId: String, driverId: Int?): Result<RideHistoryModel>

}