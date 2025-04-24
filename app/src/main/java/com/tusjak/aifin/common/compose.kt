package com.tusjak.aifin.common

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tusjak.aifin.R
import com.tusjak.aifin.data.TransactionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalTime

typealias M  = Modifier
typealias D  = R.drawable
typealias RS = R.string

@Composable
fun <T> mutable(init: T): MutableState<T> =
    remember { mutableStateOf(init) }

@Composable
fun <T> saveable(init: T): MutableState<T> =
    rememberSaveable { mutableStateOf(init) }

@Composable
fun Int.string() = stringResource(this)

@Composable
fun TransactionType.toStringRepresentation(): String {
    return when (this) {
        TransactionType.INCOME -> RS.income.string()
        TransactionType.EXPENSE -> RS.expense.string()
    }
}

@Composable
fun getGreetingByTime(): String {
    val currentHour = LocalTime.now().hour
    return when {
        currentHour in 0..10  -> RS.good_morning.string()
        currentHour in 11..16 -> RS.good_day.string()
        else                        -> RS.good_evening.string()
    }
}

@Composable
fun rememberLaunch(): (block: suspend CoroutineScope.() -> Unit) -> Unit {
    val scope = rememberCoroutineScope()
    return { block -> scope.launch { block() } }}

inline fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier =
    if (condition)
        then(modifier(Modifier))
    else
        this