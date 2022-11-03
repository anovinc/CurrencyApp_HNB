package com.example.hnbcurrencyapp.networking

import com.example.hnbcurrencyapp.data.Currency
import com.example.hnbcurrencyapp.data.CurrencyDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("tecajn/v2")
    fun getCurrency() : Call<List<Currency>>

    @GET("tecajn/v2")
    fun getCurrencyDetails(@Query("valuta") id: String) : Call<List<CurrencyDetails>>
}