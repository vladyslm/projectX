package com.myapps.projectx.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R
import com.myapps.projectx.data.calendar_events.CalendarEvent

class ChildCalendarEventAdapter(private var events: List<CalendarEvent>) :
    RecyclerView.Adapter<ChildCalendarEventAdapter.ViewHolder>() {

    private var TAG = "ChildCalendarEventAdapter"

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(event: CalendarEvent) {
            itemView.findViewById<TextView>(R.id.eventCalendarCellText).text = event.eventText
            itemView.findViewById<TextView>(R.id.eventCalendarCellDate).text = event.date.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "creating view holder")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.calendar_event_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentEvent = events[position]
        holder.bind(currentEvent)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateEvents(events: ArrayList<CalendarEvent>) {
        this.events = events
        Log.d(TAG, "data updated")
        notifyDataSetChanged()
    }

}