package com.example.hnbcurrencyapp.ui.main

import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.hnbcurrencyapp.R
import com.example.hnbcurrencyapp.databinding.ActivityMainBinding
import com.example.hnbcurrencyapp.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onPostViewCreated() {
        title = getString(R.string.HNB_exchange_rate)
        setTheme(R.style.Theme_HNBCurrencyApp)
    }

}