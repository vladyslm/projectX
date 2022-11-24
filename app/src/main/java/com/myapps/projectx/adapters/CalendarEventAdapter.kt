package com.myapps.projectx.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R
import com.myapps.projectx.data.calendar_events.CalendarEvent
import com.myapps.projectx.data.calendar_events.CalendarEventViewModel
import com.myapps.projectx.libs.DateConverter
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

class CalendarEventAdapter(
    private var calendarEventViewModel: CalendarEventViewModel,
    private var daysOfMonth: ArrayList<String>,
    private var currentDay: String,
    chosenMonthYear: LocalDate

) : RecyclerView.Adapter<CalendarEventAdapter.ViewHolder>() {

    private val TAG = "CalendarEventAdapter"
    private val PATTERN = "dd/MM/yyyy"

    private var _mapEvents = mutableMapOf<Int, ArrayList<CalendarEvent>>()

    private lateinit var context: Context
    private var allEvents: List<CalendarEvent> = ArrayList()

    init {
        val yearMonth = YearMonth.from(chosenMonthYear)
        val curMonth = yearMonth.month.value
        val year = yearMonth.year
        val numberOfDays = yearMonth.lengthOfMonth()

        val fromStr = "1/${curMonth}/$year"
        val toStr = "$numberOfDays/$curMonth/$year"

        val fromDate = DateConverter.DateStringToTimestamp(fromStr, PATTERN, Locale.ENGLISH)
        val toDate = DateConverter.DateStringToTimestamp(toStr, PATTERN, Locale.ENGLISH)

        calendarEventViewModel.getEventsInRange(fromDate!!, toDate!!)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun prepareDayTextView(day: TextView, data: String) {
            day.text = data

            if (currentDay == day.text) {
                day.background = ContextCompat.getDrawable(context, R.drawable.calendar_active_cell)
            } else {
                day.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        fun bind(data: String) {
            val day = itemView.findViewById<TextView>(R.id.calendarEventDate)
            prepareDayTextView(day, data)

            val events =
                if (_mapEvents[data.toInt()] != null) _mapEvents[data.toInt()] else ArrayList()

            setChildRecyclerView(itemView, events!!.toList())
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setEvents(events: List<CalendarEvent>) {
        allEvents = events

        val eventMap = mutableMapOf<Int, ArrayList<CalendarEvent>>()

        events.forEach {
            val localDate = DateConverter.timestampToLocalDate(it.date)
            if (eventMap[localDate.dayOfMonth] != null) {
                eventMap[localDate.dayOfMonth]?.add(it)
            } else {
                val list = ArrayList<CalendarEvent>()
                list.add(it)
                eventMap[localDate.dayOfMonth] = list
            }
        }

        _mapEvents = eventMap
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_event_placeholder, parent, false)
        val cell = view.findViewById<TextView>(R.id.calendarEventDate)
        cell.setBackgroundColor(Color.TRANSPARENT)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(daysOfMonth[position])
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    fun setChildRecyclerView(itemView: View, events: List<CalendarEvent>) {
        val childAdapter = ChildCalendarEventAdapter(events)
        val childRecyclerView = itemView.findViewById<RecyclerView>(R.id.calendarChildRV)
        childRecyclerView.layoutManager = LinearLayoutManager(context)
        childRecyclerView.adapter = childAdapter
    }
}