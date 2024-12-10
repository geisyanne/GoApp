package co.geisyanne.goapp.ui.travelRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.geisyanne.goapp.data.request.RideEstimateRequest
import co.geisyanne.goapp.domain.model.RideEstimateModel
import co.geisyanne.goapp.domain.usecase.GetRideEstimateUseCase
import kotlinx.coroutines.launch

class TravelRequestViewModel(
    private val getRideEstimateUseCase: GetRideEstimateUseCase
) : ViewModel() {

    private val _rideEstimate = MutableLiveData<RideEstimateModel>()
    val rideEstimate: LiveData<RideEstimateModel> get() = _rideEstimate

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRideEstimate(request: RideEstimateRequest) {
        viewModelScope.launch {
            val result = getRideEstimateUseCase(request)
            result.onSuccess { estimate ->
                _rideEstimate.value = estimate
            }.onFailure { exception ->
                _error.value = exception.message
            }
        }
    }
}
