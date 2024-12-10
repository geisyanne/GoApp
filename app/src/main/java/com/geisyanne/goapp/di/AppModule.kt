package com.geisyanne.goapp.di

import com.geisyanne.goapp.data.repository.RideEstimateRepositoryImpl
import com.geisyanne.goapp.domain.repository.RideEstimateRepository
import com.geisyanne.goapp.domain.usecase.GetRideEstimateUseCase
import com.geisyanne.goapp.ui.features.travelRequest.TravelRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<RideEstimateRepository> { RideEstimateRepositoryImpl(get()) }

    single { GetRideEstimateUseCase(get()) }

    viewModel { TravelRequestViewModel(get()) }
}