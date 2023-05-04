package com.example.basic_banking_app.ui.transactions

import androidx.lifecycle.ViewModel
import com.example.basic_banking_app.data.model.BankRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {

    val allTransactions = bankRepository.allTransactions
}
