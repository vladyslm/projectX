package com.myapps.projectx.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R

class CalendarEventAdapter(
    private var daysOfMonth: ArrayList<String>,
    private var currentDay: String
) : RecyclerView.Adapter<CalendarEventAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val day = holder.itemView.findViewById<TextView>(R.id.calendarEventDate)
        day.text = daysOfMonth[position]

        if (currentDay == day.text) {
            day.background = ContextCompat.getDrawable(context, R.drawable.calendar_active_cell)
        } else {
            day.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}