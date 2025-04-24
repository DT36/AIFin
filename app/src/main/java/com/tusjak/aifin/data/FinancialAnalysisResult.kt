package com.tusjak.aifin.data

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import com.google.gson.Gson
import com.tusjak.aifin.common.getDeviceLanguage
import java.text.SimpleDateFormat
import java.util.*

data class FinancialAnalysisResult(
    val summary        : String,
    val recommendations: List<String>
)

class TransactionAnalyzer {
    private val vertexAI = Firebase.vertexAI
    private val model = vertexAI.generativeModel(modelName = "gemini-2.0-flash")

    suspend fun analyzeTransactions(
        context: Context,
        transactions: List<Transaction>,
        categories: List<Category>
    ): FinancialAnalysisResult? {
        val language = getDeviceLanguage()

        val categoriesString = categories.joinToString(separator = "\n") {
            "${it.id}: ${context.getString(it.name)} (type: ${it.type})"
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val serializableTransactions = transactions.map {
            mapOf(
                "id"          to it.id,
                "title"       to it.title,
                "amount"      to it.amount,
                "date"        to dateFormat.format(it.date),
                "description" to it.description,
                "category"    to it.category,
                "type"        to it.type.toString(),
            )
        }
        val transactionsJson = Gson().toJson(serializableTransactions)

        val promptText = """
            Analyze the following list of user financial transactions and provide a concise analysis of their financial behavior. Respond in ${language} language. Use category names in text not category number. Transactions are in JSON format:
            $transactionsJson

            Categories are defined as:
            $categoriesString

            Tasks:
            1. Identify key behavior patterns:
               - Which expense categories are most frequent and what is their share of total expenses?
               - Are there regular incomes or expenses?
            2. Assess financial health:
               - What is the income-to-expense ratio? Is the user in surplus or deficit?
               - Are there signs of overspending in specific categories?
            3. Provide recommendations:
               - Suggest 2-4 specific steps to improve financial health (e.g., reduce spending in a specific category, create a budget).

            Output format (JSON):
            {
              "summary": "Concise text summary (max. 200 words) in ${language}",
              "recommendations": ["Recommendation 1", "Recommendation 2", ...]
            }
        """.trimIndent()

        return try {
            val response = model.generateContent(promptText)
            val responseText = response.text.orEmpty().trim()
            val cleanJson = responseText.replace("```json", "").replace("```", "").trim()
            Gson().fromJson(cleanJson, FinancialAnalysisResult::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}