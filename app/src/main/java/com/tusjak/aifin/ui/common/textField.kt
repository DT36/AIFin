package com.tusjak.aifin.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.common.RS
import com.tusjak.aifin.common.mutable
import com.tusjak.aifin.common.string
import com.tusjak.aifin.theme.body1
import com.tusjak.aifin.theme.body1Bold
import com.tusjak.aifin.theme.body2Bold
import com.tusjak.aifin.theme.lightGreen
import com.tusjak.aifin.theme.radius5
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import ui.common.AfText

@Composable
fun AfTextField(label: String, value: MutableState<TextFieldValue>, keyboardType: KeyboardType = KeyboardType.Text, amountValue: Boolean = false, enabled: Boolean = true) {

    Column(
        modifier = M
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        AfText(
            text     = label,
            maxLines = 1,
            color    = textColor.value,
            style    = body2Bold
        )

        OutlinedTextField(
            value         = value.value,
            onValueChange = { value.value = it },
            modifier      = M
                .fillMaxWidth()
                .background(
                    color = lightGreen,
                    shape = radius5
                ),
            enabled      = enabled,
            trailingIcon = {
                AfText(
                    modifier = M.padding(end = 8.dp),
                    text     = if (amountValue) "€" else "",
                    maxLines = 1,
                    style    = body1Bold
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction    = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor    = Color.Black,
                unfocusedLabelColor  = Color.Gray,
                disabledBorderColor  = Color.Transparent,
            ),
            textStyle = body1
        )
    }
}

@Preview
@Composable
private fun AfTextFieldPreview() {
    val textState = mutable(TextFieldValue("53.69"))

    AfTextField(label = RS.amount.string(), value = textState)
}