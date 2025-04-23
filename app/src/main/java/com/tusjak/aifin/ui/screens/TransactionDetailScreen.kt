package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.tusjak.aifin.common.toStringRepresentation
import com.tusjak.aifin.data.Transaction
import com.tusjak.aifin.data.TransactionType
import com.tusjak.aifin.ui.common.AfButton
import com.tusjak.aifin.ui.common.AfDatePicker
import com.tusjak.aifin.ui.common.AfTextField
import com.tusjak.aifin.ui.common.CenteredColumn
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen
import com.tusjak.aifin.ui.common.buttonDanger
import java.util.Date

@Composable
fun TransactionDetailScreen(
    transaction        : Transaction?,
    onEdit             : (String, String, Double, Date, Int, String, TransactionType) -> Unit,
    onDeleteTransaction: (String) -> Unit
) {
    TwoColorBackgroundScreen(
        contentOnGreen = {},
        contentOnWhite = {
            transaction?.let { transaction ->
                val title        = mutable(TextFieldValue(transaction.title))
                val amount       = mutable(TextFieldValue(transaction.amount.toString()))
                val description  = mutable(TextFieldValue(transaction.description))
                val type         = mutable(TextFieldValue(transaction.type.toStringRepresentation()))
                val selectedDate = mutable(transaction.date)
                val scrollState  = rememberScrollState()

                CenteredColumn(modifier = M.verticalScroll(scrollState).padding(16.dp).imePadding()) {
                    AfDatePicker(date = transaction.date) { selectedDate.value = it }
                    AfTextField(label = RS.title.string(), title)
                    AfTextField(
                        label = RS.amount.string(),
                        amount,
                        keyboardType = KeyboardType.Number,
                        amountValue = true
                    )
                    AfTextField(label = RS.description.string(), description)
                    AfTextField(label = RS.type.string(), type, enabled = false)

                    AfButton(
                        modifier = M.padding(start = 48.dp, end = 48.dp, top = 16.dp, bottom = 8.dp),
                        text     = stringResource(R.string.delete),
                        style    = buttonDanger
                    ) {
                        onDeleteTransaction(transaction.id)
                    }

                    AfButton(
                        modifier = M.padding(horizontal = 48.dp, vertical = 8.dp),
                        text     = stringResource(R.string.save),
                    ) {
                        onEdit(
                            transaction.id,
                            title.value.text,
                            amount.value.text.toDoubleWithCommaOrDot() ?: 0.0,
                            selectedDate.value,
                            transaction.category,
                            description.value.text,
                            transaction.type
                        )
                    }
                }
            }
        }
    )
}