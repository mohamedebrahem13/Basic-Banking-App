package com.example.basic_banking_app.ui.trensfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_banking_app.data.model.BankRepository
import com.example.basic_banking_app.data.model.Client
import com.example.basic_banking_app.data.model.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {

    private val _completeTask = MutableLiveData<Boolean>()
    val completeTask = _completeTask
    val clientlistLivedata = MutableLiveData< List<Client?>>()

    init {
        getAllClients()
    }

    private fun getAllClients() {
        viewModelScope.launch {
            bankRepository.getClients().collect {
                clientlistLivedata.value = it
            }
        }
    }

    fun insertTransaction(amount:Double,transferor:String,client:Client){
        val transaction = Transaction(System.currentTimeMillis(), transferor, client.name, amount)
        viewModelScope.launch {
            val insertionResult = bankRepository.insertTransactionAndUpdate(transaction)
            if((insertionResult.getOrDefault(-1) > 0)){
                _completeTask.value = true
            }
        }
    }
}