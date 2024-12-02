package com.teikk.datn_admin.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeConstant {
    fun getCurrentDateStr(format: String = "yyyy-MM-dd"): String {
        val now = Date()
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return formatter.format(now)
    }

    fun getCurrentDate(): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.parse(getCurrentDateStr())?: Date()
    }

    fun getCurrentTimestampStr(): String {
        val now = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(now)
    }

    fun datetimeToString(dateTime: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return formatter.format(dateTime)
    }

    fun stringToDateTime(dateTimeString: String): Date {
        // Try to parse using the "yyyy-MM-dd HH:mm:ss" format first
        return try {
            val formatterWithSpace = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            formatterWithSpace.parse(dateTimeString) ?: Date()
        } catch (e: ParseException) {
            // If it fails, try parsing with the "T" format
            val formatterWithT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            formatterWithT.parse(dateTimeString) ?: Date()
        }
    }
}
