package co.geisyanne.goapp.data.repository

import co.geisyanne.goapp.data.api.ApiService
import co.geisyanne.goapp.data.mapper.toDomain
import co.geisyanne.goapp.data.request.RideEstimateRequest
import co.geisyanne.goapp.domain.model.RideEstimateModel
import co.geisyanne.goapp.domain.repository.RideEstimateRepository
import retrofit2.HttpException

class RideEstimateRepositoryImpl(
    private val apiService: ApiService
) : RideEstimateRepository{

    override suspend fun getRideEstimate(request: RideEstimateRequest): RideEstimateModel {
        return try {
            val response = apiService.getRideEstimate(request)

            if (response.isSuccessful) {
                // Mapeia a resposta apenas se o body não for nulo
                val body = response.body()
                if (body != null) {
                    body.toDomain() // Conversão para o modelo de domínio
                } else {
                    throw IllegalStateException("Resposta do servidor está vazia.")
                }
            } else {
                // Lida com erros da API
                throw HttpException(response)
            }
        } catch (e: Exception) {
            // Lida com exceções de rede ou de parsing
            throw mapExceptionToDomain(e)
        }
    }

    // Método auxiliar para mapear exceções
    private fun mapExceptionToDomain(exception: Exception): Throwable {
        return when (exception) {
            is HttpException -> {
                // Lida com respostas de erro do servidor
                val errorBody = exception.response()?.errorBody()?.string()
                IllegalStateException("Erro do servidor: $errorBody", exception)
            }
            is IllegalStateException -> exception
            else -> RuntimeException("Erro inesperado", exception)
        }
    }

}