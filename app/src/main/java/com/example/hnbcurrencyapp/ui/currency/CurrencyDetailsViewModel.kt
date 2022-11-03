package com.example.hnbcurrencyapp.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnbcurrencyapp.coroutines.CoroutineContextProvider
import com.example.hnbcurrencyapp.coroutines.CoroutineContextProviderImpl
import com.example.hnbcurrencyapp.data.CurrencyDetails
import com.example.hnbcurrencyapp.networking.onError
import com.example.hnbcurrencyapp.networking.onSuccess
import com.example.hnbcurrencyapp.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CurrencyDetailsViewModel(
    private val currencyRepository: CurrencyRepository,
    private val coroutine: CoroutineContextProviderImpl
) : ViewModel() {

    private val _currenciesState = MutableLiveData<CurrencyListState>()
    val currenciesState: LiveData<CurrencyListState>
        get() = _currenciesState

    private val _currency = MutableLiveData<List<CurrencyDetails>>()
    val currency: LiveData<List<CurrencyDetails>>
        get() = _currency

    fun getCurrencyDetails(id: String, isNetworkAvailable: Boolean) {
        setCurrencyListState(CurrencyListState.Loading)
        if (isNetworkAvailable) {
            viewModelScope.launch(coroutine.io) {
                currencyRepository.getCurrencyDetails(id).onSuccess {
                    if (it.isEmpty()) {
                        setCurrencyListState(CurrencyListState.NoData)
                    } else {
                        _currency.postValue(it)
                        setCurrencyListState(CurrencyListState.ShowData)
                    }
                }
                    .onError {
                        setCurrencyListState(CurrencyListState.ErrorBackend)
                    }
            }
        } else {
            setCurrencyListState(CurrencyListState.NoInternet)
        }
    }

    private fun setCurrencyListState(state: CurrencyListState) {
        _currenciesState.postValue(state)
    }

    sealed class CurrencyListState {
        object NoInternet : CurrencyListState()
        object NoData : CurrencyListState()
        object Loading : CurrencyListState()
        object ShowData : CurrencyListState()
        object ErrorBackend : CurrencyListState()
    }
}