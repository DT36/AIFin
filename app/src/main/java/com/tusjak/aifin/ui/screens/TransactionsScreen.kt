package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen

@Composable
fun TransactionsScreen() {
    TwoColorBackgroundScreen(
        contentOnGreen = {
            Column(modifier = M.padding(32.dp)) {
                Text("Transactions", style = headline4)
            }

        },
        contentOnWhite = {}
    )
}

@Composable
@Preview
fun TransactionsScreenPreview() {
    TransactionsScreen()
}