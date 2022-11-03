package com.example.hnbcurrencyapp.ui.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnbcurrencyapp.coroutines.CoroutineContextProviderImpl
import com.example.hnbcurrencyapp.data.Currency
import com.example.hnbcurrencyapp.networking.onError
import com.example.hnbcurrencyapp.networking.onSuccess
import com.example.hnbcurrencyapp.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CurrencyListViewModel(private val currencyRepository: CurrencyRepository,
                            private val coroutine: CoroutineContextProviderImpl
) : ViewModel() {

    private val _currenciesState = MutableLiveData<CurrencyListState>()
    val currenciesState: LiveData<CurrencyListState>
        get() = _currenciesState

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>>
        get() = _currencies

    fun getCurrencyList(isNetworkAvailable: Boolean) {
        if (isNetworkAvailable) {
            setCurrencyListState(CurrencyListState.Loading)
            viewModelScope.launch(coroutine.io) {
                currencyRepository.getCurrencyData().onSuccess {
                    if (it.isEmpty()) {
                        setCurrencyListState(CurrencyListState.NoData)
                    } else {
                        _currencies.postValue(it)
                        setCurrencyListState(CurrencyListState.ShowData)
                    }
                }
                    .onError {
                        setCurrencyListState(CurrencyListState.ErrorBackend)
                    }
            }
        }
        else {
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