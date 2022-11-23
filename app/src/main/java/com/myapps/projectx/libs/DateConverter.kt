package com.myapps.projectx.libs

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class DateConverter {
    companion object {
        fun DateStringToTimestamp(dateStr: String, pattern: String, locale: Locale): Long? {
            val formatter = SimpleDateFormat(pattern, locale)
            val date = formatter.parse(dateStr)
            return date?.time
        }

        fun timestampToLocalDate(timestamp: Long): LocalDate {
            val date = Date(timestamp)
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }
    }

}