package com.tusjak.aifin.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.tusjak.aifin.data.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> get() = _transactions

    private var transactionListener: ListenerRegistration? = null

    init {
        setupListener() // Inicializácia pri vytvorení ViewModelu
    }

    private fun setupListener() {
        transactionListener?.remove() // Odstránime starý listener

        auth.currentUser?.let { user ->
            println("Setting up listener for user: ${user.uid}")

            transactionListener = db.collection("transactions")
                .whereEqualTo("userId", user.uid)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        println("Listener error: $error")
                        return@addSnapshotListener
                    }

                    snapshot?.let {
                        val transactionList = it.documents.map { doc ->
                            Transaction(
                                id       = doc.id,
                                title    = doc.getString("title") ?: "",
                                amount   = doc.getDouble("amount") ?: 0.0,
                                date     = doc.getDate("date") ?: java.util.Date(),
                                category = doc.getString("category") ?: "",
                                type     = doc.getString("type") ?: "expense",
                                userId   = doc.getString("userId") ?: ""
                            )
                        }

                        println("Transactions updated" + ": ${transactionList.size}")
                        _transactions.value = transactionList
                    }
                }
        } ?: run {
            println("No authenticated user, clearing transactions")
            _transactions.value = emptyList()
        }
    }

    fun addTransaction(title: String, amount: Double, category: String, type: String) {
        auth.currentUser?.let { user ->
            println("Adding transaction for user: ${user.uid}")
            val transaction = hashMapOf(
                "title"    to title,
                "amount"   to amount,
                "date"     to java.util.Date(),
                "category" to category,
                "type"     to type,
                "userId"   to user.uid
            )
            viewModelScope.launch {
                db.collection("transactions").add(transaction)
                    .addOnSuccessListener { docRef ->
                        println("Transaction added successfully with ID: ${docRef.id}")
                    }
                    .addOnFailureListener { e ->
                        println("Failed to add transaction: $e")
                    }
            }
        } ?: println("Cannot add transaction: No authenticated user")
    }

    fun deleteTransaction(transactionId: String) {
        auth.currentUser?.let { user ->
            println("Deleting transaction with ID: $transactionId for user: ${user.uid}")

            viewModelScope.launch {
                db.collection("transactions").document(transactionId)
                    .delete()
                    .addOnSuccessListener {
                        println("Transaction deleted successfully: $transactionId")
                    }
                    .addOnFailureListener { e ->
                        println("Failed to delete transaction: $e")
                    }
            }
        } ?: println("Cannot delete transaction: No authenticated user")
    }

    fun getTransactionById(transactionId: String): Transaction? {
        return _transactions.value.find { it.id == transactionId }
    }

    fun onAuthStateChanged() {
        setupListener() // Obnoví listener pri zmene autentifikácie
    }

    override fun onCleared() {
        transactionListener?.remove() // Odstráni listener pri zničení ViewModelu
        super.onCleared()
    }
}