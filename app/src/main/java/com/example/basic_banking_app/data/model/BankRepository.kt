package com.example.basic_banking_app.data.model

import androidx.lifecycle.LiveData
import com.example.basic_banking_app.data.db.BankDao
import kotlinx.coroutines.flow.Flow

class BankRepository(private val bankDao: BankDao) {

    fun getClients(): Flow<List<Client>> {
        return bankDao.getClients()
    }

    val allTransactions: Flow<List<Transaction>> = bankDao.getTransactions()

    suspend fun insertTransactionAndUpdate(transaction: Transaction): Result<Int>{
        return try {
            Result.success(bankDao.insertAndUpdateTransaction(transaction))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}