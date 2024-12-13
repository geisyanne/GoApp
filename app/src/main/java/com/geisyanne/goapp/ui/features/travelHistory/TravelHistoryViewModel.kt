package com.geisyanne.goapp.ui.features.travelHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geisyanne.goapp.domain.model.RideHistoryModel
import com.geisyanne.goapp.domain.usecase.GetRideHistoryUseCase
import com.geisyanne.goapp.domain.usecase.RideHistoryResult
import com.geisyanne.goapp.util.SingleLiveEvent
import kotlinx.coroutines.launch

class TravelHistoryViewModel(
    private val getRideHistoryUseCase: GetRideHistoryUseCase,
) : ViewModel() {

    private val _uiState = SingleLiveEvent<TravelHistoryState>()
    val uiState: SingleLiveEvent<TravelHistoryState> get() = _uiState

    fun getRideHistory(customerId: String, driverId: Int?) {
        _uiState.value = TravelHistoryState.Loading

        viewModelScope.launch {
            val result = getRideHistoryUseCase(
                customerId = customerId,
                driverId = driverId
            )

            when (result) {
                is RideHistoryResult.Success -> {
                    _uiState.value = TravelHistoryState.Success(result.rideHistory)
                }

                is RideHistoryResult.Failure -> {
                    _uiState.value = TravelHistoryState.Error(result.resId)
                }
            }
        }
    }
}

sealed class TravelHistoryState {
    data object Loading : TravelHistoryState()
    data class Success(val rideHistory: RideHistoryModel) : TravelHistoryState()
    data class Error(val resId: String?) : TravelHistoryState()
}
