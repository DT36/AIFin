package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.data.Transaction

@Composable
fun TransactionDetailScreen(transaction: Transaction?) {
    transaction?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Názov: ${it.title}")
            Text("Suma: ${it.amount}€")
            Text("Dátum: ${it.date}")
            Text("Kategória: ${it.category}")
            Text("Typ: ${it.type}")
        }
    } ?: Text("Transakcia nenájdená")
}