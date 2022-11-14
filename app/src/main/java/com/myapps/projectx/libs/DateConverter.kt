package com.myapps.projectx.libs

import java.text.SimpleDateFormat
import java.util.Locale

class DateConverter {
    companion object {
        fun DateStringToTimestamp(dateStr: String, pattern: String, locale: Locale): Long? {
            val formatter = SimpleDateFormat(pattern, locale)
            val date = formatter.parse(dateStr)
            return date?.time
        }
    }

}