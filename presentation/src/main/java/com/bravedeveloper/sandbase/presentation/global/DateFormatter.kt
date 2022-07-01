package com.bravedeveloper.sandbase.presentation.global

import android.content.Context
import android.os.Build
import com.bravedeveloper.sandbase.R
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    companion object {
        fun getFormattedDateForOrderString(context: Context, date: String): String {
            val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Locale(context.resources.configuration.locales[0].toLanguageTag())
            } else {
                Locale(context.resources.configuration.locale.toString())
            }

            val newMonths = context.resources.getStringArray(R.array.months_after_day)

            val dfs: DateFormatSymbols = DateFormatSymbols.getInstance(locale)
            dfs.months = newMonths
            val df: DateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale)
            val sdf = df as SimpleDateFormat
            sdf.applyPattern("d MMMM, HH:mm:ss")
            sdf.dateFormatSymbols = dfs

            val jud: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(date)
            return sdf.format(jud)
        }
    }

}