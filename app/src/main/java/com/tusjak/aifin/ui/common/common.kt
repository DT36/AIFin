package com.tusjak.aifin.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.common.M
import com.tusjak.aifin.theme.background
import com.tusjak.aifin.theme.mainGreen
import com.tusjak.aifin.theme.value

@Composable
inline fun CenteredRow(
    modifier   : Modifier = Modifier,
    horizontal : Arrangement.Horizontal = Arrangement.Start,
    content    : @Composable RowScope.() -> Unit) = Row(modifier, horizontal, Alignment.CenterVertically, content)

@Composable
inline fun CenteredColumn(
    modifier : Modifier = Modifier,
    vertical : Arrangement.Vertical = Arrangement.Top,
    content  : @Composable ColumnScope.() -> Unit) = Column(modifier, vertical, Alignment.CenterHorizontally, content)

@Composable
inline fun CenteredBox(
    modifier : Modifier = Modifier,
    content  : @Composable BoxScope.() -> Unit) = Box(modifier, Alignment.Center, content = content)

@Composable
fun TwoColorBackgroundScreen(
    contentOnGreen: @Composable BoxScope.() -> Unit,
    contentOnWhite: @Composable BoxScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainGreen.value)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            contentOnGreen()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp))
                .background(background.value)
        ) {
            contentOnWhite()
        }
    }
}