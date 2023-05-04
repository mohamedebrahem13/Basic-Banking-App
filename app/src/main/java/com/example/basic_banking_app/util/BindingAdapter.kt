package com.example.basic_banking_app.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*

// Loads the balance of the client with the defined currency which is Egyptian pound.
@BindingAdapter("loadBalance")
fun bindTextViewToDisplayBalance(textView: TextView, balance: Double) {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.maximumFractionDigits = 2
    numberFormat.currency = Currency.getInstance("EGP")
    textView.text = numberFormat.format(balance)
}