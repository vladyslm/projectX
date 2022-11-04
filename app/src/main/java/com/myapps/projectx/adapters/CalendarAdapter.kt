package com.myapps.projectx.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R

class CalendarAdapter(
    private var daysOfMonth: ArrayList<String>,
    private var currentDayValue: String
) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.2).toInt()
        val cell = view.findViewById<TextView>(R.id.cellDayText)
        cell.setBackgroundColor(Color.TRANSPARENT)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]

        val day = holder.itemView.findViewById<TextView>(R.id.cellDayText)
        if (day.text == currentDayValue) {
            day.background = ContextCompat.getDrawable(day.context, R.drawable.calendar_active_cell)
        }

        holder.itemView.findViewById<TextView>(R.id.cellDayText).setOnClickListener {
            val cell = it.findViewById<TextView>(it.id)
            currentDayValue = cell.text.toString()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

}