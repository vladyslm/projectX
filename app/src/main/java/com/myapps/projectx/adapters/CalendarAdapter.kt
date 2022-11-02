package com.myapps.projectx.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R

class CalendarAdapter(private var daysOfMonth: ArrayList<String>) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.2).toInt()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]

        holder.itemView.findViewById<TextView>(R.id.cellDayText).setOnClickListener {
            val cell = it.findViewById<TextView>(it.id)
            // TODO: handle click
            println(cell.text)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}