package com.example.hnbcurrencyapp.di

import com.example.hnbcurrencyapp.coroutines.CoroutineContextProviderImpl
import com.example.hnbcurrencyapp.networking.CurrencyApiService
import com.example.hnbcurrencyapp.ui.currency.CurrencyListViewModel
import com.example.hnbcurrencyapp.repository.CurrencyRepository
import com.example.hnbcurrencyapp.ui.currency.CurrencyDetailsFragment
import com.example.hnbcurrencyapp.ui.currency.CurrencyDetailsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.hnb.hr/"

val networkingModule = module {

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }
    single {
        OkHttpClient.Builder()
            .build()
    }
    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .baseUrl(BASE_URL)
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(CurrencyApiService::class.java)
    }

}

val repositoryModule = module {
    single { CurrencyRepository(get()) }
}

val coroutineModule = module {
    single { CoroutineContextProviderImpl() }
}
val viewModelModule = module {
    viewModel { CurrencyListViewModel(get(), get()) }
    viewModel { CurrencyDetailsViewModel(get(), get()) }

}

val modules = listOf(networkingModule, coroutineModule, repositoryModule, viewModelModule)