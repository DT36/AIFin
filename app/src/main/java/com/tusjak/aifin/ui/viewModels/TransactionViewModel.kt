package com.tusjak.aifin.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.tusjak.aifin.data.FinancialAnalysisResult
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionAnalyzer
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.data.categories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class TransactionViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> get() = _transactions

    private val _analysisResult = MutableStateFlow<FinancialAnalysisResult?>(null)
    val analysisResult: StateFlow<FinancialAnalysisResult?> = _analysisResult.asStateFlow()

    private var transactionListener: ListenerRegistration? = null

    init {
        setupListener() // Inicializácia pri vytvorení ViewModelu
    }

    private fun setupListener() {
        transactionListener?.remove() // Odstránime starý listener

        auth.currentUser?.let { user ->
            Log.d("TAG", "Setting up listener for user: ${user.uid}")

            transactionListener = db.collection("transactions")
                .whereEqualTo("userId", user.uid)
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        Log.e("TAG", "Listener error: $error")
                        return@addSnapshotListener
                    }

                    snapshot?.let {
                        val transactionList = it.documents.map { doc ->
                            Transaction(
                                id          = doc.id,
                                title       = doc.getString("title") ?: "",
                                amount      = doc.getDouble("amount") ?: 0.0,
                                date        = doc.getDate("date") ?: Date(),
                                description = doc.getString("description") ?: "",
                                category    = doc.getLong("category")?.toInt() ?: 9,
                                type        = try {
                                    TransactionType.valueOf(doc.getString("type") ?: "EXPENSE")
                                } catch (e: IllegalArgumentException) {
                                    TransactionType.EXPENSE
                                },
                                userId      = doc.getString("userId") ?: ""
                            )
                        }

                        Log.d("TAG", "Transactions updated" + ": ${transactionList.size}")
                        _transactions.value = transactionList
                    }
                }
        } ?: run {
            Log.d("TAG", "No authenticated user, clearing transactions")
            _transactions.value = emptyList()
        }
    }

    fun addTransaction(title: String, amount: Double, date: Date, category: Int, description: String, type: TransactionType) {
        auth.currentUser?.let { user ->
            Log.d("TAG", "Adding transaction for user: ${user.uid}")
            val transaction = hashMapOf(
                "title"       to title,
                "amount"      to amount,
                "date"        to date,
                "description" to description,
                "category"    to category,
                "type"        to type.name,
                "userId"      to user.uid
            )
            viewModelScope.launch {
                db.collection("transactions").add(transaction)
                    .addOnSuccessListener { docRef ->
                        Log.d("TAG", "Transaction added successfully with ID: ${docRef.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.d("TAG", "Failed to add transaction: $e")
                    }
            }
        } ?: Log.d("TAG", "Cannot add transaction: No authenticated user")
    }

    fun editTransaction(transactionId: String, title: String, amount: Double, date: Date, category: Int, description: String, type: TransactionType) {
        auth.currentUser?.let { user ->
            Log.d("TAG", "Editing transaction with ID: $transactionId for user: ${user.uid}")
            val updatedTransaction = hashMapOf(
                "title"       to title,
                "amount"      to amount,
                "date"        to date,
                "description" to description,
                "category"    to category,
                "type"        to type.name,
                "userId"      to user.uid
            )
            viewModelScope.launch {
                db.collection("transactions").document(transactionId)
                    .set(updatedTransaction)
                    .addOnSuccessListener {
                        Log.d("TAG", "Transaction updated successfully: $transactionId")
                    }
                    .addOnFailureListener { e ->
                        Log.d("TAG", "Failed to update transaction: $e")
                    }
            }
        } ?: Log.d("TAG", "Cannot edit transaction: No authenticated user")
    }

    fun deleteTransaction(transactionId: String) {
        auth.currentUser?.let { user ->
            Log.d("TAG", "Deleting transaction with ID: $transactionId for user: ${user.uid}")

            viewModelScope.launch {
                db.collection("transactions").document(transactionId)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("TAG", "Transaction deleted successfully: $transactionId")
                    }
                    .addOnFailureListener { e ->
                        Log.d("TAG", "Failed to delete transaction: $e")
                    }
            }
        } ?: Log.d("TAG", "Cannot delete transaction: No authenticated user")
    }

    fun getTransactionById(transactionId: String): Transaction? {
        return _transactions.value.find { it.id == transactionId }
    }

    fun onAuthStateChanged() {
        setupListener() // Obnoví listener pri zmene autentifikácie
    }

    fun analyzeTransactions(context: android.content.Context) {
        if (transactions.value.isEmpty()) {
            _analysisResult.value = FinancialAnalysisResult(
                summary = "Nie sú dostupné žiadne transakcie na analýzu.",
                recommendations = emptyList()
            )
            return
        }
        viewModelScope.launch {
            val analyzer = TransactionAnalyzer()
            _analysisResult.value = analyzer.analyzeTransactions(
                context = context,
                transactions = transactions.value,
                categories = categories
            )
        }
    }

    override fun onCleared() {
        transactionListener?.remove() // Odstráni listener pri zničení ViewModelu
        super.onCleared()
    }
}