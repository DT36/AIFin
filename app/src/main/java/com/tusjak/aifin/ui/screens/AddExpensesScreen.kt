package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.common.toDoubleWithCommaOrDot
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.ui.common.AfButton
import com.tusjak.aifin.ui.common.AfDatePicker
import com.tusjak.aifin.ui.common.AfTextField
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import java.util.Date

@Composable
fun AddExpensesScreen(
    onAddExpense: (String, Double, Date, String, String, TransactionType) -> Unit,
) {
    TwoColorBackgroundScreen(
        contentOnGreen = {

        },
        contentOnWhite = {
            val title        = mutable(TextFieldValue(""))
            val amount       = mutable(TextFieldValue(""))
            val description  = mutable(TextFieldValue(""))
            val selectedDate = mutable(Date())

            CenteredColumn(modifier = M.padding(16.dp)) {
                AfDatePicker { selectedDate.value = it }
                AfTextField(label = RS.title.string(), title)
                AfTextField(label = RS.amount.string(), amount, keyboardType = KeyboardType.Number, amountValue = true)
                AfTextField(label = RS.description.string(), description)

                AfButton(
                    modifier = M.padding(horizontal = 48.dp, vertical = 32.dp),
                    text     = stringResource(R.string.save),
                    enabled  = title.value.text.isNotEmpty()
                ) {
                    onAddExpense(
                        title.value.text,
                        amount.value.text.toDoubleWithCommaOrDot() ?: 0.0,
                        selectedDate.value,
                        "category",
                        description.value.text,
                        TransactionType.EXPENSE
                    )
                }
            }
        }
    )
}