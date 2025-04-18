package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.textColor
import com.tusjak.aifin.theme.value
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen

@Composable
fun AnalysisScreen() {
    TwoColorBackgroundScreen(
        contentOnGreen = {

        },
        contentOnWhite = {}
    )
}

@Composable
@Preview
fun AnalysisScreenPreview() {
    AnalysisScreen()
}