package com.example.hnbcurrencyapp.ui.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hnbcurrencyapp.R
import com.example.hnbcurrencyapp.common.isNetworkAvailable
import com.example.hnbcurrencyapp.data.Currency
import com.example.hnbcurrencyapp.ui.currency.adpters.CurrencyListAdapter
import com.example.hnbcurrencyapp.databinding.FragmentCurrencyListBinding
import com.example.hnbcurrencyapp.extensions.gone
import com.example.hnbcurrencyapp.extensions.invisible
import com.example.hnbcurrencyapp.extensions.subscribe
import com.example.hnbcurrencyapp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment : BaseFragment<FragmentCurrencyListBinding>() {

    private val viewModel: CurrencyListViewModel by viewModel()
    private val currencyListAdapter by lazy {
        CurrencyListAdapter{  currency ->
            goToCurrencyDetails(currency)
        }
    }

    private fun goToCurrencyDetails(currency: Currency) {
        val action = CurrencyListFragmentDirections.actionCurrencyListFragmentToCurrencyConverterFragment(currency.currencyName)
        findNavController().navigate(action)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrencyListBinding
        get() = FragmentCurrencyListBinding::inflate

    override fun onPostViewCreated() {
        setupRecycler()
        viewModel.getCurrencyList(activity.isNetworkAvailable())
        subscribeData()

     }

    private fun showData(currencyList: List<Currency>) {
        currencyListAdapter.refreshData(currencyList)
    }

    private fun setupRecycler() {
        with(binding.recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = currencyListAdapter
        }
    }

    private fun handleCurrencyListState(state: CurrencyListViewModel.CurrencyListState) {
        when(state) {
            CurrencyListViewModel.CurrencyListState.NoInternet -> showNoInternet()
            CurrencyListViewModel.CurrencyListState.NoData -> showNoData()
            CurrencyListViewModel.CurrencyListState.ErrorBackend -> showBackhandError()
            CurrencyListViewModel.CurrencyListState.ShowData -> showData()

        }
    }

    private fun showNoInternet() {
        with(binding) {
            recycler.invisible()
            progressBar.invisible()
            infoTextView.text = getString(R.string.no_internet)
        }
    }

    private fun showNoData() {
        with(binding) {
            recycler.invisible()
            progressBar.invisible()
            infoTextView.text = getString(R.string.no_data)
        }
    }

    private fun showBackhandError() {
        with(binding){
            recycler.invisible()
            progressBar.invisible()
            infoTextView.text = getString(R.string.backhand_error)
        }
    }

    private fun showData() {
        with(binding){
            progressBar.invisible()
            infoTextView.gone()
        }

    }

    private fun subscribeData() {
        viewModel.currenciesState.subscribe(this, ::handleCurrencyListState)
        viewModel.currencies.subscribe(this, ::showData)
    }
}