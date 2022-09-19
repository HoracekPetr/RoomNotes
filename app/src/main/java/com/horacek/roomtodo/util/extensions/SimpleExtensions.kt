package com.horacek.roomtodo.util.extensions

import android.text.Editable
import java.text.SimpleDateFormat
import java.util.*

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}