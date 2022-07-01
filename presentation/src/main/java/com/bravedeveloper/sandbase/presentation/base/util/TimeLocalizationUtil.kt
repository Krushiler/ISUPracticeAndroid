package com.bravedeveloper.sandbase.presentation.base.util

import android.content.res.Resources
import com.bravedeveloper.domain.model.city.hours.ReceivingHoursEnum
import com.bravedeveloper.sandbase.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun parseToDate(dateStr: String, resources: Resources): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date = dateFormat.parse(dateStr)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return "${calendar.get(Calendar.DAY_OF_MONTH)} ${
        resources.getStringArray(R.array.months_after_day)[calendar.get(
            Calendar.MONTH
        )]
    }"
}

fun getHoursStringFromHoursEnum(hours: ReceivingHoursEnum, resources: Resources): String {
    return if (hours == ReceivingHoursEnum.EVENING) {
        resources.getString(R.string.receiving_hours_evening)
    } else {
        resources.getString(R.string.receiving_hours_morning)
    }
}

fun getOrderFullTime(dateStr: String, hours: ReceivingHoursEnum, resources: Resources) =
    "${parseToDate(dateStr, resources)}\n${getHoursStringFromHoursEnum(hours, resources)}"

fun getTimesAgoString(dateStr: String, resources: Resources): String {
    val currentTime = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val orderTime = dateFormat.parse(dateStr)

    val timeDifference = currentTime.time - orderTime.time

    val second = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
    val minute = TimeUnit.MILLISECONDS.toMinutes(timeDifference)
    val hour = TimeUnit.MILLISECONDS.toHours(timeDifference)
    val day = TimeUnit.MILLISECONDS.toDays(timeDifference)

    val suffix = resources.getString(R.string.ago)

    val result = when {
        second < 60 -> {
            "$second ${resources.getQuantityString(R.plurals.seconds_ago, second.toInt())} $suffix"
        }
        minute < 60 -> {
            "$minute ${resources.getQuantityString(R.plurals.minutes_ago, minute.toInt())} $suffix"
        }
        hour < 24 -> {
            "$hour ${resources.getQuantityString(R.plurals.hours_ago, hour.toInt())} $suffix"
        }
        else -> {
            "$day ${resources.getQuantityString(R.plurals.days_ago, day.toInt())} $suffix"
        }
    }

    return result
}