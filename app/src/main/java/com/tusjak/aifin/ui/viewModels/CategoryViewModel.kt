package com.tusjak.aifin.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tusjak.aifin.data.TransactionType

class CategoryViewModel : ViewModel() {

    private val _selectedTransactionType = mutableStateOf(TransactionType.EXPENSE)
    val selectedTransactionType: State<TransactionType> = _selectedTransactionType

    fun setSelectedTransactionType(type: TransactionType) {
        _selectedTransactionType.value = type
    }
}