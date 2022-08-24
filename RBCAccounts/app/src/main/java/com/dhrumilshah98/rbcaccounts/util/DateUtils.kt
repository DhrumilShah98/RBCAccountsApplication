package com.dhrumilshah98.rbcaccounts.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * `DateUtils` to convert date into required format.
 */
object DateUtils {

    // Date Formats
    const val DATE_FORMAT_1 = "EEE, MMM dd, yyyy"

    @JvmStatic
    fun formatDate(date: Date, dateFormat: String): String {
        return SimpleDateFormat(dateFormat, Locale.getDefault()).format(date)
    }
}