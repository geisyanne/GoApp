package com.geisyanne.goapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geisyanne.goapp.domain.usecase.RideEstimateResult

class SharedViewModel : ViewModel() {

    private val _rideEstimate = MutableLiveData<RideEstimateResult?>()
    val rideEstimate: LiveData<RideEstimateResult?> get() = _rideEstimate

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

}

