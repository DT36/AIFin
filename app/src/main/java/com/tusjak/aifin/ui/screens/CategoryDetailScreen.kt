package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.tusjak.aifin.common.D
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toEuroAmount
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.data.categories
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.caribbeanGreen
import com.tusjak.aifin.theme.honeydew
import com.tusjak.aifin.theme.oceanBlue
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.CenteredRow
import com.tusjak.aifin.ui.common.ShowDateRangePickerDialog
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoryDetailScreen(
    categoryId: Int?,
    transactions: StateFlow<List<Transaction>>,
    onTransactionClick: (String) -> Unit
) {
    val transactionList by transactions.collectAsState()
    val transactionsByCategory = transactionList.filter { it.category == categoryId }
    val category   = categoryId?.let { categories[it] }
    val isIncome   = category?.type == TransactionType.INCOME

    var dateRange by mutable<Pair<Long, Long>?>(null)

    val filteredTransactions = dateRange?.let { (start, end) ->
        transactionsByCategory.filter {
            it.date.time in start..end
        }
    } ?: transactionsByCategory

    val incomeSum  = filteredTransactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expenseSum = filteredTransactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

    TwoColorBackgroundScreen(
        contentOnGreen = {
            Row(M.padding(horizontal = 32.dp, vertical = 16.dp)) {

                CenteredRow(M.fillMaxWidth(), horizontal = Arrangement.SpaceBetween) {

                    Column(horizontalAlignment = Alignment.Start) {

                        Row(
                            horizontalArrangement = spacedBy4,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(if (isIncome) D.income else D.expense),
                                colorFilter = ColorFilter.tint(if (isIncome) caribbeanGreen else oceanBlue),
                                contentDescription = RS.total_balance.string(),
                            )

                            Text(
                                text = if (isIncome) RS.total_income.string() else RS.total_expense.string(),
                                style = body2,
                                color = textColor.value
                            )
                        }

                        Text(
                            text = if (isIncome) incomeSum.toEuroAmount() else expenseSum.toEuroAmount(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = honeydew
                        )
                    }

                    category?.let {
                        Image(
                            modifier           = M.size(100.dp),
                            painter            = painterResource(it.drawableRes),
                            contentDescription = it.name.string(),
                        )
                    }
                }
            }
        },
        contentOnWhite = {
            var showDialog by remember { mutableStateOf(false) }

            Image(
                painter            = painterResource(id = D.ic_calendar),
                contentDescription = RS.select_date.string(),
                modifier           = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .zIndex(1f)
                    .clickable { showDialog = true }
            )

            if (showDialog) {
                ShowDateRangePickerDialog(
                    onDateRangeSelected = { startDate, endDate ->
                        dateRange = startDate.time to endDate.time
                    },
                    onDismiss = { showDialog = false }
                )
            }

            TransactionList(filteredTransactions, onTransactionClick)
        }
    )
}