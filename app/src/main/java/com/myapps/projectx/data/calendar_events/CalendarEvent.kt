package com.myapps.projectx.data.calendar_events

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calendar_events")
data class CalendarEvent(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventText: String,
    val from: Long,
    val to: Long,
    val color: String,
    val date: Long
)

