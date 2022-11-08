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

interface CustomAdapter {
    fun updateCurrDate(date: String)
}

class CalendarAdapter(
    private var daysOfMonth: ArrayList<String>,
    private var currentDayValue: String,
    private var onItemListener: OnItemListener
) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>(), CustomAdapter {


    interface OnItemListener {
        fun onItemClick(position: Int, value: String)
    }


    class ViewHolder(itemView: View, onItemListener: OnItemListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
        private var onItemListener: OnItemListener

        init {
            this.onItemListener = onItemListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.2).toInt()
        val cell = view.findViewById<TextView>(R.id.cellDayText)
        cell.setBackgroundColor(Color.TRANSPARENT)
        return ViewHolder(view, onItemListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateCurrDate(date: String) {
        currentDayValue = date
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]

        val day = holder.itemView.findViewById<TextView>(R.id.cellDayText)
        if (day.text == currentDayValue) {
            day.background = ContextCompat.getDrawable(day.context, R.drawable.calendar_active_cell)
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

}