package com.example.hnbcurrencyapp.repository

import com.example.hnbcurrencyapp.data.Currency
import com.example.hnbcurrencyapp.data.CurrencyDetails
import com.example.hnbcurrencyapp.networking.*

const val DEFAULT_ERROR_CODE = 407

class CurrencyRepository(private val apiService: CurrencyApiService) {

    suspend fun getCurrencyData(): Result<List<Currency>> {
        apiService.getCurrency()
            .awaitResult()
            .onSuccess {
                return Success(it)
            }
            .onError {
                return handleError(it)
            }
        return Failure(HttpError(Throwable(), DEFAULT_ERROR_CODE))
    }

    suspend fun getCurrencyDetails(id: String): Result<List<CurrencyDetails>> {
        apiService.getCurrencyDetails(id)
            .awaitResult()
            .onSuccess {
                return Success(it)
            }
            .onError {
                return handleError(it)
            }
        return Failure(HttpError(Throwable(), DEFAULT_ERROR_CODE))
    }

    private fun handleError(error: HttpError): Failure {
        return Failure(error)
    }
}