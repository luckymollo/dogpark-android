package com.luckymollo.dogpark.core.data

import androidx.room.TypeConverter
import java.util.*

object RoomTypeConverters {

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}
