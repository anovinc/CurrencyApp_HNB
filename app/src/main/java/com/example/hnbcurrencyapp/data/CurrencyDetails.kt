package com.example.hnbcurrencyapp.data

import com.google.gson.annotations.SerializedName

/*
[
{
"broj_tecajnice": "42",
"datum_primjene": "2019-03-01",
"drzava": "Australija",
"drzava_iso": "AUS",
"sifra_valute": "036",
"valuta": "AUD",
"jedinica": 1,
"kupovni_tecaj": "4,631630",
"srednji_tecaj": "4,645567",
"prodajni_tecaj": "4,659504"
}
]
 */
data class CurrencyDetails(
    @SerializedName("drzava")
    val country: String,
    @SerializedName("drzava_iso")
    val countryIso: String,
    @SerializedName("valuta")
    val currencyName: String,
    @SerializedName("srednji_tecaj")
    val currencyValue: String,
    @SerializedName("prodajni_tecaj")
    val sellingCurrencyValue: String,
    @SerializedName("kupovni_tecaj")
    val buyingCurrencyValue: String
)