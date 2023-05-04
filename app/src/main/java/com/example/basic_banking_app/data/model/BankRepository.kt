package com.example.basic_banking_app.data.model

import androidx.lifecycle.LiveData
import com.example.basic_banking_app.data.db.BankDao

class BankRepository(private val bankDao: BankDao) {

    fun getClients(): LiveData<List<Client>> {
        return bankDao.getClients()
    }

    val allTransactions: LiveData<List<Transaction>> = bankDao.getTransactions()

    suspend fun insertTransactionAndUpdate(transaction: Transaction): Result<Int>{
        return try {
            Result.success(bankDao.insertAndUpdateTransaction(transaction))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}