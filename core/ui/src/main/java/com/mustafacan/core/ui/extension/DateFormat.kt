package com.mustafacan.core.ui.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.formatAsLocalDateTime(): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = isoFormat.parse(this)

        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this
    }
}