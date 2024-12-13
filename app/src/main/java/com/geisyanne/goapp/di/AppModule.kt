package com.geisyanne.goapp.di

import com.geisyanne.goapp.data.repository.RideConfirmRepositoryImpl
import com.geisyanne.goapp.data.repository.RideEstimateRepositoryImpl
import com.geisyanne.goapp.data.repository.RideHistoryRepositoryImpl
import com.geisyanne.goapp.domain.repository.RideConfirmRepository
import com.geisyanne.goapp.domain.repository.RideEstimateRepository
import com.geisyanne.goapp.domain.repository.RideHistoryRepository
import com.geisyanne.goapp.domain.usecase.GetRideConfirmUseCase
import com.geisyanne.goapp.domain.usecase.GetRideEstimateUseCase
import com.geisyanne.goapp.domain.usecase.GetRideHistoryUseCase
import com.geisyanne.goapp.ui.SharedViewModel
import com.geisyanne.goapp.ui.features.travelHistory.TravelHistoryViewModel
import com.geisyanne.goapp.ui.features.travelOptions.TravelOptionsViewModel
import com.geisyanne.goapp.ui.features.travelRequest.TravelRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<RideEstimateRepository> { RideEstimateRepositoryImpl(get()) }
    single { GetRideEstimateUseCase(get()) }

    viewModel { TravelRequestViewModel(get()) }

    viewModel { SharedViewModel() }

    single<RideConfirmRepository> { RideConfirmRepositoryImpl(get()) }
    single { GetRideConfirmUseCase(get()) }

    viewModel { TravelOptionsViewModel(get()) }

    single<RideHistoryRepository> { RideHistoryRepositoryImpl(get()) }
    single { GetRideHistoryUseCase(get()) }

    viewModel { TravelHistoryViewModel(get()) }

}