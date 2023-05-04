package com.example.basic_banking_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.basic_banking_app.data.model.Client
import com.example.basic_banking_app.data.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Client::class, Transaction::class], version = 1)
abstract class BankDatabase : RoomDatabase(){

    abstract fun bankDao(): BankDao

    companion object {
        @Volatile
        private var INSTANCE: BankDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): BankDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, BankDatabase::class.java, "bank_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(BankDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class BankDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.bankDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(bankDao: BankDao) {
            val clients = mutableListOf<Client>()
            clients.add(Client("mohamed", "mohamedebrahem1447@gamil.com", "01554988710", 4000.00))
            clients.add(Client("sara", "sara@gamil.com", "012347844", 2000.00))
            clients.add(Client("jonny", "jonny@gamil.com", "01287344375", 9000.00))
            clients.add(Client("mark", "mark@gamil.com", "0124554345", 16000.00))
            clients.add(Client("Steven", "steven@gamil.com", "0120309348", 5000.00))
            clients.add(Client("Jennifer", "jennifer@gamil.com", "0129454845", 80000.00))
            clients.add(Client("Robert", "robert@gamil.com", "01278343932", 1000.00))
            clients.add(Client("James", "james@gamil.com", "01245643545", 3000.00))
            clients.add(Client("adam", "adam@gamil.com", "01299993456", 50000.00))
            clients.add(Client("Jessica", "jessica@gamil.com", "0124345535", 7000.00))
            clients.shuffle()
            clients.shuffle()
            bankDao.insertClients(clients)
        }
    }
}