package com.myapps.projectx.fragments

import android.os.Bundle
import android.os.Process
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.R
import com.myapps.projectx.adapters.CalendarAdapter
import com.myapps.projectx.adapters.CalendarEventAdapter
import com.myapps.projectx.customLayouts.CustomNonScrollableGridLayout
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: LocalDate

    private lateinit var calendarEventAdapter: CalendarEventAdapter
    private lateinit var calendarEventRecyclerView: RecyclerView

    private var days: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDate = LocalDate.now()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: remove dummy data
        days.add("4")
        days.add("5")
        days.add("6")
        days.add("7")
        days.add("8")
        days.add("9")
        days.add("10")
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidgets()
        setMonthView()

        calendarEventAdapter = CalendarEventAdapter(days)
        calendarEventRecyclerView = view.findViewById(R.id.calendarEventsRecyclerView)
        calendarEventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        calendarEventRecyclerView.adapter = calendarEventAdapter
    }

    private fun initWidgets() {
        calendarRecyclerView = requireView().findViewById(R.id.calendarRecyclerView)
        monthYearText = requireView().findViewById(R.id.calendarMonthTextView)
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, selectedDate.dayOfMonth.toString())
        val layoutManager = CustomNonScrollableGridLayout(requireContext(), 7)

        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

}