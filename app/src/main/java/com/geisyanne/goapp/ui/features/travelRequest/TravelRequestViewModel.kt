package com.geisyanne.goapp.ui.features.travelRequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geisyanne.goapp.domain.usecase.GetRideEstimateUseCase
import com.geisyanne.goapp.domain.usecase.RideEstimateResult
import com.geisyanne.goapp.util.SingleLiveEvent
import kotlinx.coroutines.launch

class TravelRequestViewModel(
    private val getRideEstimateUseCase: GetRideEstimateUseCase,
) : ViewModel() {

    private val _uiState = SingleLiveEvent<TravelRequestState>()
    val uiState: SingleLiveEvent<TravelRequestState> get() = _uiState

    fun getRideEstimate(customerId: String, origin: String, destination: String) {
        _uiState.value = TravelRequestState.Loading

        viewModelScope.launch {
            when (val result = getRideEstimateUseCase(customerId, origin, destination)) {
                is RideEstimateResult.Success -> {
                    _uiState.value = TravelRequestState.Success(result)
                }
                is RideEstimateResult.Failure -> {
                    _uiState.value = TravelRequestState.Error(result.resId)
                }
            }
        }
    }
}

sealed class TravelRequestState {
    object Idle : TravelRequestState()
    object Loading : TravelRequestState()
    data class Success(val rideEstimate: RideEstimateResult) : TravelRequestState()
    data class Error(val resId: String?) : TravelRequestState()
}
