package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toEuroAmount
import com.tusjak.aifin.common.toFormattedDate
import com.tusjak.aifin.common.toStringRepresentation
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.theme.oceanBlue
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.body2Bold
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.radius4
import com.tusjak.aifin.theme.spacedBy16
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.theme.subtitle
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.theme.vividBlue
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.CenteredRow
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import kotlinx.coroutines.flow.StateFlow
import java.time.ZoneId
import java.util.Locale

@Composable
fun TransactionsScreen(
    transactions: StateFlow<List<Transaction>>,
    onDeleteTransaction: (String) -> Unit,
    onTransactionClick: (String) -> Unit
) {
    val transactionList by transactions.collectAsState()
    val incomeSum = transactionList.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expenseSum =
        transactionList.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val totalSum = incomeSum.minus(expenseSum)

    TwoColorBackgroundScreen(
        contentOnGreen = {
            Column(modifier = M.padding(32.dp), verticalArrangement = spacedBy16) {
                Text(RS.title_transactions.string(), style = headline4)

                CenteredRow(
                    M
                        .clip(radius4)
                        .background(background.value)
                        .fillMaxWidth()
                        .padding(16.dp), horizontal = Arrangement.Center
                ) {
                    CenteredColumn(vertical = spacedBy4) {
                        Text(RS.total_balance.string(), color = textColor.value, style = body2)
                        Text(
                            text = totalSum.toEuroAmount(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value,
                        )
                    }
                }

                Row(M.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {

                    CenteredColumn(
                        M
                            .weight(1f)
                            .clip(radius4)
                            .background(background.value)
                            .padding(horizontal = 32.dp, vertical = 16.dp), vertical = spacedBy4
                    ) {
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.income),
                            colorFilter = ColorFilter.tint(mainGreen.value),
                            contentDescription = RS.income.string(),
                        )
                        Text(RS.income.string(), color = textColor.value, style = body2)
                        Text(
                            text = incomeSum.toEuroAmount(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value
                        )
                    }

                    CenteredColumn(
                        M
                            .weight(1f)
                            .clip(radius4)
                            .background(background.value)
                            .padding(horizontal = 32.dp, vertical = 16.dp), vertical = spacedBy4
                    ) {
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.expense),
                            colorFilter = ColorFilter.tint(oceanBlue),
                            contentDescription = RS.expenses.string(),
                        )
                        Text(RS.expenses.string(), color = textColor.value, style = body2)
                        Text(
                            text = expenseSum.toEuroAmount(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value
                        )
                    }
                }
            }
        },
        contentOnWhite = {
            TransactionList(transactionList, onDeleteTransaction)
        }
    )
}

@Composable
private fun TransactionList(
    transactions: List<Transaction>,
    onDeleteTransaction: (String) -> Unit
) {
    val groupedTransactions = transactions
        .groupBy {
            val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate.year to localDate.month
        }
        .toSortedMap { (year1, month1), (year2, month2) ->
            compareValuesBy(
                Pair(year2, month2),
                Pair(year1, month1),
                { it.first },
                { it.second }
            )
        }

    LazyColumn(M.padding(top = 8.dp)) {
        groupedTransactions.forEach { (yearMonth, transactionsInMonth) ->
            item {
                val monthName = yearMonth.second.getDisplayName(
                    java.time.format.TextStyle.FULL_STANDALONE,
                    Locale.getDefault()
                ).replaceFirstChar { it.uppercase() }

                MonthItem(monthName)
            }

            items(transactionsInMonth) { transaction ->
                TransactionItem(transaction, onDeleteTransaction)
            }
        }
    }
}

@Composable
private fun TransactionItem(transaction: Transaction, onDeleteTransaction: (String) -> Unit) {
    CenteredRow(
        M
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onDeleteTransaction(transaction.id) },

        horizontal = spacedBy4
    ) {

        Image(
            painter = painterResource(R.drawable.icon_salary),
            contentDescription = "Info",
        )

        CenteredColumn(modifier = M.width(100.dp)) {

            Text(
                text = transaction.title,
                color = textColor.value,
                textAlign = TextAlign.Center,
                style = body2Bold
            )

            Text(
                text = transaction.date.toFormattedDate(),
                color = vividBlue,
                textAlign = TextAlign.Center,
                style = body2,
            )
        }

        VerticalDivider(M.height(32.dp), thickness = 2.dp, color = mainGreen.value)

        Text(
            modifier = M.width(70.dp),
            text = transaction.type.toStringRepresentation(),
            color = textColor.value,
            textAlign = TextAlign.Center,
            style = body2
        )

        VerticalDivider(M.height(32.dp), thickness = 2.dp, color = mainGreen.value)

        Text(
            modifier = M.fillMaxWidth(),
            text = transaction.amount.toEuroAmount(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor.value,
            style = subtitle
        )
    }
}

@Composable
private fun MonthItem(month: String) {
    Text(
        modifier = M.padding(16.dp),
        text = month,
        color = textColor.value,
        textAlign = TextAlign.Center,
        style = body2Bold
    )
}