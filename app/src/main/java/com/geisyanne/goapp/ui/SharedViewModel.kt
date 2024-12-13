package com.geisyanne.goapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.geisyanne.goapp.domain.model.InputDataModel
import com.geisyanne.goapp.domain.usecase.RideEstimateResult
import com.geisyanne.goapp.util.SingleLiveEvent

class SharedViewModel : ViewModel() {

    private val _rideEstimate = SingleLiveEvent<RideEstimateResult?>()
    val rideEstimate: LiveData<RideEstimateResult?> get() = _rideEstimate

    private val _travelRequestData = SingleLiveEvent<InputDataModel?>()
    val travelRequestData: LiveData<InputDataModel?> get() = _travelRequestData

    fun setRideEstimate(result: RideEstimateResult) {
        _rideEstimate.value = result
    }

    fun clearRideEstimate() {
        _rideEstimate.value = null
    }

    fun getRideEstimate(): RideEstimateResult? {
        return _rideEstimate.value
    }

    fun setTravelRequestData(customerId: String, origin: String, destination: String) {
        _travelRequestData.value = InputDataModel(customerId, origin, destination)
    }

    fun getTravelRequestData(): InputDataModel? {
        return _travelRequestData.value
    }

}

