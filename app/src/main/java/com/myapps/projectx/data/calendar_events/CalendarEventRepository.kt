package com.myapps.projectx.data.calendar_events

import androidx.lifecycle.LiveData

class CalendarEventRepository(private val calendarEventDao: CalendarEventDao) {

    suspend fun addEvent(event: CalendarEvent) {
        calendarEventDao.addEvent(event)
    }

    fun readAllEvents(date: Long): LiveData<List<CalendarEvent>> {
        return calendarEventDao.readAllEvents(date)
    }

    fun readAll() :LiveData<List<CalendarEvent>>{
        return calendarEventDao.readAll()
    }

    fun readEventsInRange(from: Long, to: Long): LiveData<List<CalendarEvent>> {
        return calendarEventDao.readEventsInRange(from, to)
    }

}