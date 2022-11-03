package com.example.hnbcurrencyapp.ui.currency.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hnbcurrencyapp.data.Currency
import com.example.hnbcurrencyapp.databinding.ItemBinding
import com.example.hnbcurrencyapp.ui.currency.adpters.CurrencyHolder

class CurrencyListAdapter(private val onCurrencyClicked:(Currency) -> Unit) : RecyclerView.Adapter<CurrencyHolder>() {

    private val currencies: MutableList<Currency> = mutableListOf()

    fun refreshData(currencies: List<Currency>) {
        this.currencies.clear()
        this.currencies.addAll(currencies)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        return CurrencyHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCurrencyClicked
        )
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        holder.bindData(currencies[position])
    }

    override fun getItemCount(): Int = currencies.size


}