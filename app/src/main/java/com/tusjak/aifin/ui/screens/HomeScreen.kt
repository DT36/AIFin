package com.tusjak.aifin.ui.screens

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.getGreetingByTime
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toEuroAmount
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.body2Bold
import com.tusjak.aifin.theme.oceanBlue
import com.tusjak.aifin.theme.button
import com.tusjak.aifin.theme.caribbeanGreen
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.honeydew
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.theme.void
import com.tusjak.aifin.ui.common.AfTextField
import com.tusjak.aifin.ui.common.CenteredRow
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import kotlinx.coroutines.flow.StateFlow
import ui.common.AfText

@Composable
fun HomeScreen(
    transactions      : StateFlow<List<Transaction>>,
    onTransactionClick: (String) -> Unit
) {
    val transactionList by transactions.collectAsState()
    val incomeSum  = transactionList.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val expenseSum = transactionList.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val totalSum   = incomeSum.minus(expenseSum)

    TwoColorBackgroundScreen(
        contentOnGreen = {
            BalanceOverview(totalSum, expenseSum)
        },
        contentOnWhite = {
            if (transactionList.isEmpty()) {
                EmptyTransactionList()
            } else {
                LastTransactionsList(transactionList, onTransactionClick)
            }
        }
    )
}


@Composable
fun BalanceOverview(total: Double, expenses: Double, showGoal: Boolean = true) {
    val context     = LocalContext.current
    val showDialog  = mutable(false)
    val sharedPrefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }

    if (showDialog.value) {
        SetGoalDialog(showDialog, sharedPrefs, context)
    }

    Column(
        modifier = M
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Row(
            modifier = M.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {

                Row(horizontalArrangement = spacedBy4, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter            = painterResource(R.drawable.income),
                        colorFilter        = ColorFilter.tint(caribbeanGreen),
                        contentDescription = RS.total_balance.string(),
                    )

                    Text(
                        text = RS.total_balance.string(),
                        fontSize = 14.sp,
                        color = textColor.value
                    )
                }

                Text(
                    text = total.toEuroAmount(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = honeydew
                )
            }

            VerticalDivider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = Color.White
            )

            Column(horizontalAlignment = Alignment.End) {

                Row(horizontalArrangement = spacedBy4, verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter            = painterResource(R.drawable.expense),
                        colorFilter        = ColorFilter.tint(oceanBlue),
                        contentDescription = RS.total_expense.string(),
                    )

                    Text(
                        text = RS.total_expense.string(),
                        fontSize = 14.sp,
                        color = textColor.value
                    )
                }

                Text(
                    text       = expenses.toEuroAmount(),
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = textColor.value
                )
            }
        }

        if (showGoal) {
            Spacer(modifier = Modifier.height(8.dp))

            if (sharedPrefs.contains("goal")) {
                val goal = getSavedGoal(context)

                // Progress Bar Row
                Row(
                    modifier          = M.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    CustomProgressBar(
                        (total / goal).toFloat(),
                        goal.toEuroAmount()
                    ) {
                        showDialog.value = true
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = M.padding(horizontal = 12.dp),
                    horizontalArrangement = spacedBy4,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter            = painterResource(R.drawable.check),
                        colorFilter        = ColorFilter.tint(textColor.value),
                        contentDescription = "",
                    )

                    Text(
                        text = stringResource(id = R.string.goal_message, ((total / goal)*100).toInt()),
                        fontSize = 14.sp,
                        color = textColor.value
                    )
                }
            } else {
                Text(
                    modifier = M
                        .padding(vertical = 16.dp)
                        .clickable { showDialog.value = true },
                    text = RS.set_goal.string(),
                    color = oceanBlue,
                    style = button
                )
            }
        }
    }
}

@Composable
fun CustomProgressBar(progress: Float, goalAmount: String, onGoalCLick : () -> Unit) {
    Box(
        modifier = M
            .fillMaxWidth()
            .height(28.dp)
            .clip(RoundedCornerShape(50))
            .background(void),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = M
                .fillMaxHeight()
                .fillMaxWidth(1 - progress)
                .clip(RoundedCornerShape(50.dp))
                .background(honeydew),
        )

        Text(
            text       = "${(progress * 100).toInt()}%",
            fontSize   = 14.sp,
            fontStyle  = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color      = caribbeanGreen,
            modifier   = M
                .align(Alignment.CenterStart)
                .padding(start = 12.dp)
        )

        Text(
            text       = goalAmount,
            fontSize   = 14.sp,
            fontStyle  = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color      = oceanBlue,
            modifier   = M
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .clickable { onGoalCLick() }
        )
    }
}

@Composable
private fun LastTransactionsList(
    transactions      : List<Transaction>,
    onTransactionClick: (String) -> Unit
) {

    LazyColumn(contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)) {

        item {
            Text(
                modifier  = M.padding(16.dp),
                text      = RS.last_transactions.string(),
                color     = textColor.value,
                textAlign = TextAlign.Center,
                style     = body2Bold
            )
        }

        items(transactions.take(5).sortedByDescending { it.date }) { transaction ->
            TransactionItem(transaction) {
                onTransactionClick(transaction.id)
            }
        }
    }
}

fun getSavedGoal(context: Context): Double {
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPrefs.getFloat("goal", 0.0f).toDouble()
}

@Composable
private fun SetGoalDialog(showDialog: MutableState<Boolean>, sharedPrefs: SharedPreferences, context: Context) {
    val goalInput = mutable(TextFieldValue(""))

    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = {
            AfText(
                text     = RS.set_goal.string(),
                maxLines = 1,
                color    = textColor.value,
                style    = body2Bold
            )
        },
        containerColor = background.value,
        text = {
            AfTextField(label = "", value = goalInput, keyboardType = KeyboardType.Number)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val goalValue = goalInput.value.text.toDoubleOrNull()
                    if (goalValue != null) {
                        with(sharedPrefs.edit()) {
                            putFloat("goal", goalValue.toFloat())
                            apply()
                        }
                        goalInput.value = TextFieldValue("")
                        showDialog.value = false
                    } else {
                        Toast.makeText(context, RS.valid_number, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            ) {
                Text(text = RS.save.string(), color = oceanBlue, style = button)
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text(text = RS.close.string(), color = oceanBlue, style = button)
            }
        }
    )
}