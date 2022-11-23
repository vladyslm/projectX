package com.myapps.projectx.data.calendar_events

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CalendarEventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(event: CalendarEvent)

    @Query("SELECT * FROM calendar_events WHERE date = :date")
    fun readAllEvents(date: Long): LiveData<List<CalendarEvent>>

    @Query("SELECT * FROM calendar_events")
    fun readAll(): LiveData<List<CalendarEvent>>

    @Query("SELECT * FROM calendar_events WHERE :from <= date <= :to")
    fun readEventsInRange(from: Long, to: Long): LiveData<List<CalendarEvent>>

}