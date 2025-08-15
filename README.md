Basic Banking App Documentation
Overview
The Basic Banking App is an Android mobile application built using Kotlin, MVVM architecture, Room Database, Dagger Hilt for dependency injection, and StateFlow and SharedFlow for efficient data flow handling. The app enables users to perform banking transactions, view transaction details, and track transaction history. The data is persisted using Room Database, allowing users to access transaction records even when offline.

<div align="center">
  <h1 style="display: flex; align-items: center; justify-content: center; gap: 10px;">
    <img src="https://github.com/user-attachments/assets/13954c6c-ef15-4e97-ba6c-339fd30a4f25"
         alt="CineVerse Logo"
         width="50"
         style="display: inline-block;" />
    CineVerse – Where Every Story Finds Its Star
  </h1>
</div>

This repository provides the full implementation of the app, including:

Transaction Management (Transfer Money)
Transaction History (View all past transactions)
Transaction Details (Display detailed transaction information)
Architecture
The app uses MVVM (Model-View-ViewModel) architecture, which helps in separating the concerns of the app, making the code more modular and easier to maintain.

Key Technologies:
MVVM: Clear separation of concerns with Model, View, and ViewModel layers.
Room Database: For storing transactions locally.
Dagger Hilt: Dependency Injection for managing app components.
StateFlow & SharedFlow: For managing UI-related data and event-driven communication in a reactive way.

Core Features
Transaction History: View a list of all previous transactions.
Transaction Details: View detailed information about a specific transaction.
StateFlow: Used to handle UI-related data in a lifecycle-safe way, ensuring the UI responds to changes in the data efficiently.
SharedFlow: Used for managing events like showing success/failure messages, handling form submissions, and other transient UI events.
Room Database: For persistent storage of transaction data.
App Structure
Transaction Model: Represents a transaction entity in the Room database.
TransactionDao: Provides the database operations (insertion and retrieval).
TransactionRepository: Abstraction layer between the ViewModel and Room database.
TransactionViewModel: Communicates with the repository and exposes data to the UI layer using StateFlow and SharedFlow.
MainActivity/Fragment: Displays the transaction history and details, interacts with the ViewModel.
Data Flow
StateFlow is used in the ViewModel to expose the transaction data to the UI. It ensures that the UI receives updates in a lifecycle-safe manner.

SharedFlow is used to manage events such as transaction success or failure, providing a way to handle transient events that only need to be consumed once.

1. Transaction Model
@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val transactionId: Int,
    val senderAccount: String,
    val recipientAccount: String,
    val amount: Double,
    val timestamp: Long
)

2. TransactionDao
@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY timestamp DESC")
    fun getAllTransactions(): LiveData<List<Transaction>>
}

3. TransactionRepository
class TransactionRepository @Inject constructor(private val transactionDao: TransactionDao) {

    // Using StateFlow to expose transaction list
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> get() = _transactions

    suspend fun addTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
        // Refresh the transaction list after adding new transaction
        _transactions.value = transactionDao.getAllTransactions().value
    }

    fun getTransactionHistory() {
        // Retrieve transactions from the Room database
        _transactions.value = transactionDao.getAllTransactions().value
    }
}

4. TransactionViewModel
kotlin
Copy code
@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: TransactionRepository) : ViewModel() {

    // Expose transaction list to the UI
    val transactions: StateFlow<List<Transaction>> = repository.transactions

    // Event Flow to manage transaction success/failure messages
    private val _transactionEvent = MutableSharedFlow<String>()
    val transactionEvent = _transactionEvent.asSharedFlow()

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.addTransaction(transaction)
            // Emit a success message after the transaction is added
            _transactionEvent.emit("Transaction Successful")
        }
    }

    fun fetchTransactionHistory() {
        viewModelScope.launch {
            repository.getTransactionHistory()
        }
    }
}

5. MainActivity
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // Observe transactions with StateFlow
        lifecycleScope.launchWhenStarted {
            transactionViewModel.transactions.collect { transactions ->
                // Update the UI with the list of transactions
                updateTransactionList(transactions)
            }
        }

        // Observe event with SharedFlow
        lifecycleScope.launchWhenStarted {
            transactionViewModel.transactionEvent.collect { message ->
                // Show success message (toast, dialog, etc.)
                showTransactionMessage(message)
            }
        }
    }
}
How It Works
1. Saving Transactions
When a user initiates a transaction, the TransactionViewModel calls the repository to insert the transaction into the Room database. After inserting, the transaction list is updated using StateFlow.

2. Displaying Transaction History
The TransactionViewModel uses StateFlow to expose a live list of transactions to the UI. The data is collected by the UI layer (e.g., MainActivity) and displayed using a RecyclerView.

3. Handling Events with SharedFlow
When a transaction is successfully added, the TransactionViewModel emits an event using SharedFlow to notify the UI, which can then display a success message.

Conclusion
The Basic Banking App demonstrates a clean, scalable, and testable approach to handling transaction management using MVVM, Room Database, Dagger Hilt, StateFlow, and SharedFlow in an Android environment. This architecture helps to keep the app modular and maintainable, while also ensuring that the UI is updated reactively with data changes.
