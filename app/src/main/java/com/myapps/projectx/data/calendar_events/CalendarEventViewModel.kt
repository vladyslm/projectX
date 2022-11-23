package com.myapps.projectx.data.calendar_events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myapps.projectx.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarEventViewModel(application: Application) : AndroidViewModel(application) {
    private var TAG = "CalendarEventViewModel"

    private var repository: CalendarEventRepository
    var allEvents: LiveData<List<CalendarEvent>> = MutableLiveData()


    init {
        val calendarEventDao = AppDatabase.getDatabase(application).calendarEventDao()
        repository = CalendarEventRepository(calendarEventDao)
    }


    fun addEvent(event: CalendarEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addEvent(event)
        }
    }

    fun getEventsInRange(from: Long, to: Long) {
        allEvents = repository.readEventsInRange(from, to)
    }

    fun getEvents(date: Long) {
        allEvents = repository.readAllEvents(date)
    }
}