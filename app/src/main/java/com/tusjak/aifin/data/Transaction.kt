package com.tusjak.aifin.data

import java.util.Date

enum class TransactionType{
    INCOME,
    EXPENSE
}

data class Transaction(
    val id          : String = "",          // Unikátne ID transakcie (generované Firestore)
    val title       : String = "",          // Názov transakcie
    val amount      : Double = 0.0,         // Suma transakcie
    val date        : Date = Date(),        // Dátum transakcie
    val description : String = "",          // Popis transakcie
    val category    : String = "",          // Kategória
    val type        : TransactionType,      // Typ: "expense" (výdavok) alebo "income" (príjem)
    val userId      : String = ""           // ID používateľa
)