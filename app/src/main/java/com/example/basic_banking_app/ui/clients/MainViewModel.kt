package com.example.basic_banking_app.ui.clients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basic_banking_app.data.model.BankRepository
import com.example.basic_banking_app.data.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bankRepository: BankRepository) : ViewModel() {
    private val _detailedClient = MutableLiveData<Client?>()
    val detailedClient: LiveData<Client?> get() = _detailedClient

     val clientListLivedata = MutableLiveData< List<Client?>>()

    init {
        getAllClients()
    }

    // Save the value of the clicked client to be observed later.
    fun onClientClicked(Client: Client) {
        _detailedClient.value = Client
    }

    // Event for navigating to detailedFragment successfully and make the value
    // of _detailedClient null.
    fun doneNavigating() {
        _detailedClient.value = null
    }



    private fun getAllClients() {
        viewModelScope.launch {
            bankRepository.getClients().collect{
                clientListLivedata.value=it
            }
        }

    }
}