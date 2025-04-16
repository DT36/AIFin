package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.tusjak.aifin.R
import com.tusjak.aifin.common.D
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toEuroAmount
import com.tusjak.aifin.common.toFormattedDate
import com.tusjak.aifin.common.toStringRepresentation
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.body2Bold
import com.tusjak.aifin.theme.caribbeanGreen
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.oceanBlue
import com.tusjak.aifin.theme.radius4
import com.tusjak.aifin.theme.spacedBy16
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.theme.subtitle
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.theme.vividBlue
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.CenteredRow
import com.tusjak.aifin.ui.common.ShowDateRangePickerDialog
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import kotlinx.coroutines.flow.StateFlow
import java.time.Month
import java.time.ZoneId
import java.util.Locale

@Composable
fun TransactionsScreen(
    transactions: StateFlow<List<Transaction>>,
    onTransactionClick: (String) -> Unit
) {
    val transactionList by transactions.collectAsState()

    var dateRange by mutable<Pair<Long, Long>?>(null)

    val filteredTransactions = dateRange?.let { (start, end) ->
        transactionList.filter {
            it.date.time in start..end
        }
    } ?: transactionList

    val incomeSum  = filteredTransactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expenseSum = filteredTransactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val totalSum   = incomeSum.minus(expenseSum)

    TwoColorBackgroundScreen(
        contentOnGreen = {
            Column(modifier = M.padding(32.dp), verticalArrangement = spacedBy16) {
                Text(RS.title_transactions.string(), color = textColor.value, style = headline4)

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
                            .padding(horizontal = 8.dp, vertical = 16.dp), vertical = spacedBy4
                    ) {
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.income),
                            colorFilter = ColorFilter.tint(caribbeanGreen),
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
                            .padding(horizontal = 8.dp, vertical = 16.dp), vertical = spacedBy4
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

@Composable
fun TransactionList(
    transactions: List<Transaction>,
    onTransactionClick: (String) -> Unit
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

    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)) {
        groupedTransactions.forEach { (yearMonth, transactionsInMonth) ->
            item {
                MonthItem(yearMonth)
            }

            items(transactionsInMonth) { transaction ->
                TransactionItem(transaction, onTransactionClick)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction, onTransactionClick: (String) -> Unit) {
    CenteredRow(
        M
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onTransactionClick(transaction.id) },

        horizontal = spacedBy4
    ) {

        Image(
            painter = painterResource(R.drawable.ic_food),
            modifier = M.size(57.dp),
            contentDescription = "Info",
        )

        CenteredColumn(modifier = M.width(120.dp)) {

            Text(
                text      = transaction.title,
                color     = textColor.value,
                textAlign = TextAlign.Center,
                maxLines  = 1,
                overflow  = TextOverflow.Ellipsis,
                style     = body2Bold
            )

            Text(
                text      = transaction.date.toFormattedDate(),
                color     = vividBlue,
                textAlign = TextAlign.Center,
                style     = body2,
            )
        }

        VerticalDivider(M.height(32.dp), thickness = 2.dp, color = mainGreen.value)

        Text(
            modifier  = M.width(70.dp),
            text      = transaction.type.toStringRepresentation(),
            color     = textColor.value,
            textAlign = TextAlign.Center,
            style     = body2
        )

        VerticalDivider(M.height(32.dp), thickness = 2.dp, color = mainGreen.value)

        Text(
            modifier  = M.fillMaxWidth(),
            text      = transaction.amount.toEuroAmount(),
            textAlign = TextAlign.Center,
            maxLines  = 1,
            overflow  = TextOverflow.Ellipsis,
            color     = textColor.value,
            style     = subtitle
        )
    }
}

@Composable
private fun MonthItem(yearMonth: Pair<Int, Month>) {
    val monthName = yearMonth.second.getDisplayName(
        java.time.format.TextStyle.FULL_STANDALONE,
        Locale.getDefault()
    ).replaceFirstChar { it.uppercase() }

    CenteredRow(modifier  = M.padding(16.dp), horizontal = Arrangement.spacedBy(4.dp)) {
        Text(
            text      = monthName,
            color     = textColor.value,
            textAlign = TextAlign.Center,
            style     = body2Bold
        )

        Text(
            text      = yearMonth.first.toString(),
            color     = textColor.value,
            textAlign = TextAlign.Center,
            style     = body2Bold
        )
    }
}