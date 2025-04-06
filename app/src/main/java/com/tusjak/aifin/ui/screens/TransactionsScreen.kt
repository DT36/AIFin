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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.body2
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.radius4
import com.tusjak.aifin.theme.spacedBy16
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.theme.subtitle
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.CenteredRow
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TransactionsScreen(
    transactions       : StateFlow<List<Transaction>>,
    onAddTransaction   : (String, Double, String, String) -> Unit,
    onDeleteTransaction: (String) -> Unit,
    onTransactionClick : (String) -> Unit
) {
    TwoColorBackgroundScreen(offsetHeight = 300.dp,
        contentOnGreen = {
            Column(modifier = M.padding(32.dp), verticalArrangement = spacedBy16) {
                Text("Transactions", style = headline4)

                CenteredRow(M
                    .clip(radius4)
                    .background(background.value)
                    .fillMaxWidth()
                    .padding(16.dp), horizontal = Arrangement.Center) {
                    CenteredColumn(vertical = spacedBy4){
                        Text("Total Balance",color= textColor.value, style = body2)
                        Text(
                            text = "$7,783.00",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value,
                            modifier = M.clickable { onAddTransaction("test", 0.1, "Test", "Test") }
                        )
                    }
                }

                Row(M.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                    CenteredColumn(M
                        .clip(radius4)
                        .background(background.value)
                        .padding(horizontal = 32.dp, vertical = 16.dp), vertical = spacedBy4){
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.income),
                            colorFilter = ColorFilter.tint(textColor.value),
                            contentDescription = "Info",
                        )
                        Text("Income",color= textColor.value, style = body2)
                        Text(
                            text = "$7,783.00",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value
                        )
                    }

                    CenteredColumn(M
                        .clip(radius4)
                        .background(background.value)
                        .padding(horizontal = 32.dp, vertical = 16.dp), vertical = spacedBy4){
                        Image(
                            modifier = M.size(20.dp),
                            painter = painterResource(R.drawable.expense),
                            colorFilter = ColorFilter.tint(textColor.value),
                            contentDescription = "Info",
                        )
                        Text("Expenses",color= textColor.value, style = body2)
                        Text(
                            text = "$7,783.00",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor.value
                        )
                    }
                }
            }
        },
        contentOnWhite = {
            val transactionList by transactions.collectAsState()

            TransactionList(transactionList, onDeleteTransaction)
        }
    )
}

@Composable
@Preview
fun TransactionsScreenPreview() {
    //TransactionsScreen()
}

@Composable
@Preview
fun TransactionItemPreview() {
    //TransactionItem()
}

@Composable
private fun TransactionList(transactions: List<Transaction>, onDeleteTransaction: (String) -> Unit) {
    LazyColumn(M.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
        items(transactions) { transaction ->
            TransactionItem(transaction, onDeleteTransaction)
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
                style = body2
            )

            Text(
                text = "Date",
                color = textColor.value,
                textAlign = TextAlign.Center,
                style = body2
            )
        }

        VerticalDivider(M.height(32.dp), thickness = 2.dp, color = mainGreen.value)

        Text(
            modifier = M.width(70.dp),
            text = "Income",
            color = textColor.value,
            textAlign = TextAlign.Center,
            style = body2
        )

        VerticalDivider(M.height(32.dp), thickness = 2.dp, color = mainGreen.value)

        Text(
            modifier = M.fillMaxWidth(),
            text = transaction.amount.toString(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor.value,
            style = subtitle
        )
    }
}

@Composable
private fun MonthItem() {
    Text(
        modifier = M.padding(16.dp),
        text = "April",
        color = textColor.value,
        textAlign = TextAlign.Center,
        style = body2
    )
}