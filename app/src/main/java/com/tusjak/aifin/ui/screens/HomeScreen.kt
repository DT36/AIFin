package com.tusjak.aifin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tusjak.aifin.R
import com.tusjak.aifin.common.M
import com.tusjak.aifin.theme.OceanBlue
import com.tusjak.aifin.theme.fenceGreen
import com.tusjak.aifin.theme.headline4
import com.tusjak.aifin.theme.honeydew
import com.tusjak.aifin.theme.spacedBy4
import com.tusjak.aifin.ui.common.TwoColorBackgroundScreen

@Composable
fun HomeScreen() {
    TwoColorBackgroundScreen(offsetHeight = 250.dp,
        contentOnGreen = {
            Column(modifier = M.padding(32.dp)) {
                Text("Ahoj Timi", style = headline4)
                Text("Good morning")

                BalanceOverview()
            }

        },
        contentOnWhite = {

        }
    )
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun BalanceOverview() {
    Column(
        modifier = M
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Row(
            modifier = M.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {

                Row(horizontalArrangement = spacedBy4) {
                    Image(
                        painter = painterResource(R.drawable.income),
                        contentDescription = "Info",
                    )

                    Text(
                        text = "Total Balance",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

                Text(
                    text = "$7,783.00",
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

                Row(horizontalArrangement = spacedBy4) {
                    Image(
                        painter = painterResource(R.drawable.income),
                        contentDescription = "Info",
                    )

                    Text(
                        text = "Total Expense",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

                Text(
                    text = "-\$1,187.40",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = OceanBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress Bar Row
        Row(modifier = M.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CustomProgressBar(0.5f, "20000")

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = M.padding(horizontal = 12.dp),
            horizontalArrangement = spacedBy4,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.check),
                contentDescription = "Info",
            )

            Text(
                text = "30% Of Your Incomes, Looks Good.",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun CustomProgressBar(progress: Float, goalAmount: String) {
    Box(
        modifier = M
            .fillMaxWidth()
            .height(28.dp)
            .clip(RoundedCornerShape(50))
            .background(fenceGreen),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = M
                .fillMaxHeight()
                .fillMaxWidth(1-progress)
                .clip(RoundedCornerShape(50.dp))
                .background(honeydew),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = goalAmount,
                color = fenceGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = M.padding(horizontal = 12.dp).align(Alignment.CenterEnd)
            )
        }

        Text(
            text = "${(progress * 100).toInt()}%",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = honeydew,
            modifier = M
                .align(Alignment.CenterStart)
                .padding(start = 12.dp)
        )

        if (progress >= 1f) {
            Text(
                text = goalAmount,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = honeydew,
                modifier = M
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
            )
        }

    }
}