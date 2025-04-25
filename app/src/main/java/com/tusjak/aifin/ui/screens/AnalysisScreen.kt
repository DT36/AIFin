package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.subtitle
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.CenteredBox
import com.tusjak.aifin.ui.common.Spinner

import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import com.tusjak.aifin.ui.viewModels.TransactionViewModel

@Composable
fun AnalysisScreen(viewModel: TransactionViewModel) {
    val analysisResult by viewModel.analysisResult.collectAsState()
    val transactions   by viewModel.transactions.collectAsState()
    val context   = LocalContext.current
    val isLoading = mutable(true)

    LaunchedEffect(transactions) {
        if (transactions.isNotEmpty() && analysisResult == null) {
            viewModel.analyzeTransactions(context)
        }
        isLoading.value = false
    }

    TwoColorBackgroundScreen(
        contentOnGreen = {},
        contentOnWhite = {
            when{
                transactions.isEmpty()                    -> EmptyTransactionList(message = RS.empty_analysis.string())
                analysisResult == null || isLoading.value -> CenteredBox(M.fillMaxSize()) { Spinner() }
                else                                      -> {
                    LazyColumn(
                        modifier = M.fillMaxWidth(),
                        contentPadding = PaddingValues(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item { Text(RS.summary_title.string(), style = subtitle, color = textColor.value) }
                        item { Text(text = analysisResult!!.summary, style = body2, color = textColor.value) }
                        item { Text(modifier = M.padding(top = 16.dp), text = RS.recommendations.string(), style = subtitle, color = textColor.value) }

                        items(analysisResult!!.recommendations) { recommendation ->
                            Card(
                                modifier = M.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = mainGreen.value
                                )
                            ) {
                                Text(
                                    text = recommendation,
                                    modifier = M.padding(12.dp),
                                    style = body2,
                                    color = textColor.value
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}