package com.tusjak.aifin.data

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI

class TransactionCategorizer {
    private val vertexAI = Firebase.vertexAI
    private val model = vertexAI.generativeModel(modelName = "gemini-2.0-flash")

    suspend fun categorizeTransaction(
        context: Context,
        transactionName: String,
        transactionAmount: Double,
        transactionDescription: String
    ): Int? {
        val promptText = """
            Na základe nasledujúcich informácií o transakcii:
            Názov: $transactionName
            Suma: $transactionAmount
            Popis: $transactionDescription

            Vyberte najvhodnejšie ID kategórie z nasledujúceho zoznamu:
            ${categories.joinToString(separator = "\n") { "${it.id}: ${context.getString(it.name)}" }}

            Odpovedajte iba číslom ID vybranej kategórie.
        """.trimIndent()

        return try {
            val response = model.generateContent(promptText)
            val responseText = response.text.orEmpty().trim()
            responseText.toIntOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}