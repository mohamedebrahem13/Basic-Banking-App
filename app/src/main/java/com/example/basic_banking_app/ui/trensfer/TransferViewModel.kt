package com.example.basic_banking_app.ui.trensfer

import androidx.lifecycle.LiveData
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

    fun getAllClients(): LiveData<List<Client>> {
        return bankRepository.getClients()
    }

    fun insertTransaction(transaction: Transaction){
        viewModelScope.launch {
            val insertionResult = bankRepository.insertTransactionAndUpdate(transaction)
            if((insertionResult.getOrDefault(-1) > 0)){
                _completeTask.value = true
            }
        }
    }
}