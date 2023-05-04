package com.example.basic_banking_app.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basic_banking_app.data.model.BankRepository
import com.example.basic_banking_app.data.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {
    private val _detailedCustomer = MutableLiveData<Client?>()
    val detailedCustomer: LiveData<Client?> get() = _detailedCustomer

    // Save the value of the customer clicked to be observed later.
    fun onClientClicked(Client: Client) {
        _detailedCustomer.value = Client
    }

    // Event for navigating to detailedCustomerFragment successfully and make the value
    // of _detailedCustomer null.
    fun doneNavigating() {
        _detailedCustomer.value = null
    }


    fun getAllClients(): LiveData<List<Client>> {
        return bankRepository.getClients()
    }
}