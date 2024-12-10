package co.geisyanne.goapp.di

import co.geisyanne.goapp.data.repository.RideEstimateRepositoryImpl
import co.geisyanne.goapp.domain.repository.RideEstimateRepository
import co.geisyanne.goapp.domain.usecase.GetRideEstimateUseCase
import co.geisyanne.goapp.ui.travelRequest.TravelRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<RideEstimateRepository> { RideEstimateRepositoryImpl(get()) }

    single { GetRideEstimateUseCase(get()) }

    viewModel { TravelRequestViewModel(get()) }
}