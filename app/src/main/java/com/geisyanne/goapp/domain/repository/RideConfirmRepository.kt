package com.geisyanne.goapp.domain.repository

import com.geisyanne.goapp.data.request.RideConfirmRequest

interface RideConfirmRepository {

    suspend fun getRideConfirm(request: RideConfirmRequest): Result<Boolean>

}