package com.example.basic_banking_app.ui.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_banking_app.data.model.BankRepository
import com.example.basic_banking_app.data.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {
    val allTransactions = MutableLiveData< List<Transaction?>>()

    init {
        get_transaction()
    }


    private fun get_transaction (){
    viewModelScope.launch {
        bankRepository.allTransactions.collect{
            allTransactions.value=it
        }
    }

}

}
