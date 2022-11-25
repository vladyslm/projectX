package com.myapps.projectx.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R
import com.myapps.projectx.data.calendar_events.CalendarEvent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val DEFAULT_EVENT_COLOR = "#2440A1"

class ChildCalendarEventAdapter(private var events: List<CalendarEvent>) :
    RecyclerView.Adapter<ChildCalendarEventAdapter.ViewHolder>() {

    private var TAG = "ChildCalendarEventAdapter"

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private fun setCellColor(cell: LinearLayout, color: String) {
            val colorID = parseColorSafe(color)
            println(colorID)
            val resource =
                ContextCompat.getDrawable(itemView.context, R.drawable.calendar_event_cell)
            val filter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                colorID,
                BlendModeCompat.SRC_ATOP
            )
            resource?.colorFilter = filter
            cell.background = resource
        }

        private fun parseColorSafe(color: String): Int {
            val colorID = try {
                Color.parseColor(color)
            } catch (e: java.lang.Exception) {
                Color.parseColor(DEFAULT_EVENT_COLOR)
            }
            return colorID
        }

        fun bind(event: CalendarEvent) {
            val cell = itemView.findViewById<LinearLayout>(R.id.calendarEventCell)
            setCellColor(cell, event.color)

            itemView.findViewById<TextView>(R.id.eventCalendarCellText).text = event.eventText
            if (event.from != 0L) {
                var time = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(event.from)
                if (event.to != 0L) {
                    val to = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(event.to)
                    time += " - $to"
                }
                itemView.findViewById<TextView>(R.id.eventCalendarCellDate).text = time
            }
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