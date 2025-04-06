package com.tusjak.aifin.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen

@Composable
fun AddExpensesScreen() {
    TwoColorBackgroundScreen(
        offsetHeight   = 50.dp,
        contentOnGreen = {

        },
        contentOnWhite = {}
    )
}

@Composable
@Preview
fun AddExpenseScreenPreview() {
    AddExpensesScreen()
}