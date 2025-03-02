package com.tusjak.aifin.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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