package com.tusjak.aifin.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

typealias M = Modifier

@Composable
fun rememberLaunch(): (block: suspend CoroutineScope.() -> Unit) -> Unit {
    val scope = rememberCoroutineScope()
    return { block -> scope.launch { block() } }}

inline fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier =
    if (condition)
        then(modifier(Modifier))
    else
        this