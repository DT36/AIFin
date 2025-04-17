package com.tusjak.aifin.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun String.toDoubleWithCommaOrDot(): Double? {
    return try {
        this.replace(",", ".").toDouble()
    } catch (e: NumberFormatException) {
        null
    }
}

@SuppressLint("DefaultLocale")
fun Double.toEuroAmount(): String {
    return String.format("%.2f €", this)
}

fun Calendar.toFormattedDate(): String {
    val dateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
    return dateFormat.format(this.time)
}

fun Date.toFormattedDateYear(): String {
    val dateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
    return dateFormat.format(this.time)
}

fun Date.toFormattedDate(): String {
    val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
    return dateFormat.format(this)
}