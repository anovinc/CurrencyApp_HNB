package com.example.hnbcurrencyapp.ui.currency.adpters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hnbcurrencyapp.R
import com.example.hnbcurrencyapp.common.*
import com.example.hnbcurrencyapp.data.Currency
import com.example.hnbcurrencyapp.databinding.ItemBinding
import com.example.hnbcurrencyapp.extensions.loadImage
import com.example.hnbcurrencyapp.extensions.onClick

class CurrencyHolder(private val binding: ItemBinding, private val onCurrencyClicked:(Currency) -> Unit) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(currency: Currency) {
        with(binding) {
            countryTextView.text = currency.country
            currencyNameTextView.text = currency.currencyName
            currencyValueTextView.text = currency.currencyValue
            flagImageView.loadImage(countryFlags.getValue(currency.country))
            root.onClick {
                onCurrencyClicked(currency)
            }
        }

    }
}