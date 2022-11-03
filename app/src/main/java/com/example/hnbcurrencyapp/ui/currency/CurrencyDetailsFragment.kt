package com.example.hnbcurrencyapp.ui.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hnbcurrencyapp.R
import com.example.hnbcurrencyapp.common.countryFlags
import com.example.hnbcurrencyapp.common.isNetworkAvailable
import com.example.hnbcurrencyapp.data.CurrencyDetails
import com.example.hnbcurrencyapp.databinding.FragmentCurrencyDetailsBinding
import com.example.hnbcurrencyapp.extensions.gone
import com.example.hnbcurrencyapp.extensions.invisible
import com.example.hnbcurrencyapp.extensions.loadImage
import com.example.hnbcurrencyapp.extensions.subscribe
import com.example.hnbcurrencyapp.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyDetailsFragment : BaseFragment<FragmentCurrencyDetailsBinding>() {

    private val viewModel: CurrencyDetailsViewModel by viewModel()
    private val args: CurrencyDetailsFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCurrencyDetailsBinding
        get() = FragmentCurrencyDetailsBinding::inflate

    override fun onPostViewCreated() {
        viewModel.getCurrencyDetails(args.id, activity.isNetworkAvailable())
        subscribeData()
    }

    private  fun subscribeData() {
        viewModel.currenciesState.subscribe(this, ::handleCurrencyListState)
        viewModel.currency.subscribe(this, ::showData)

    }
    private fun showData(currencyDetails: List<CurrencyDetails>) {
        with(binding) {
            sellingValueTextView.text = currencyDetails[0].sellingCurrencyValue
            buyingValueTextView.text = currencyDetails[0].buyingCurrencyValue
            averageValueTextView.text = currencyDetails[0].currencyValue

            countryIsoTextView.text = currencyDetails[0].countryIso
            countryTextView.text = currencyDetails[0].country
            currencyNameTextView.text = currencyDetails[0].currencyName

            flagImageView.loadImage(countryFlags.getValue(currencyDetails[0].country))

        }
    }

    private fun handleCurrencyListState(state: CurrencyDetailsViewModel.CurrencyListState) {
        when(state) {
            CurrencyDetailsViewModel.CurrencyListState.NoInternet -> showNoInternet()
            CurrencyDetailsViewModel.CurrencyListState.NoData -> showNoData()
            CurrencyDetailsViewModel.CurrencyListState.ErrorBackend -> showBackhandError()
            CurrencyDetailsViewModel.CurrencyListState.ShowData -> showData()
        }
    }

    private fun showNoInternet() {
        with(binding) {
            itemCardView.invisible()
            progressBar.invisible()
            infoTextView.text = getString(R.string.no_internet)
        }
    }

    private fun showNoData() {
        with(binding) {
            itemCardView.invisible()
            progressBar.invisible()
            infoTextView.text = getString(R.string.no_data)
        }
    }

    private fun showBackhandError() {
        with(binding) {
            itemCardView.invisible()
            progressBar.invisible()
            infoTextView.text = getString(R.string.backhand_error)
        }
    }

    private fun showData() {
        with(binding) {
            progressBar.invisible()
            infoTextView.gone()
        }
    }

}