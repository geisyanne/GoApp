package com.geisyanne.goapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geisyanne.goapp.domain.model.TravelRequestData
import com.geisyanne.goapp.domain.usecase.RideEstimateResult

class SharedViewModel : ViewModel() {

    private val _rideEstimate = MutableLiveData<RideEstimateResult?>()
    val rideEstimate: LiveData<RideEstimateResult?> get() = _rideEstimate

    private val _travelRequestData = MutableLiveData<TravelRequestData?>()
    val travelRequestData: LiveData<TravelRequestData?> get() = _travelRequestData

    fun setRideEstimate(result: RideEstimateResult) {
        _rideEstimate.value = result
        Log.d("SharedViewModel", "RideEstimate set to: $result")
    }

    fun clearRideEstimate() {
        _rideEstimate.value = null
    }

    fun getRideEstimate(): RideEstimateResult? {
        return _rideEstimate.value
    }

    fun setTravelRequestData(customerId: String, origin: String, destination: String) {
        _travelRequestData.value = TravelRequestData(customerId, origin, destination)
    }

    fun getTravelRequestData(): TravelRequestData? {
        return _travelRequestData.value
    }


}

