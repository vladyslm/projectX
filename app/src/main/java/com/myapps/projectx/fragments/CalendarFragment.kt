package com.myapps.projectx.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.adapters.CalendarAdapter
import com.myapps.projectx.adapters.CalendarEventAdapter
import com.myapps.projectx.customLayouts.CustomNonScrollableGridLayout
import com.myapps.projectx.data.calendar_events.CalendarEventViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import com.myapps.projectx.databinding.FragmentCalendarBinding
import com.myapps.projectx.libs.DateConverter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val PATTERN = "dd/MM/yyyy"

class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {
    private var TAG = "CalendarFragment"

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding

    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: LocalDate

    private lateinit var calendarEventRecyclerView: RecyclerView

    private var days: ArrayList<String> = ArrayList()
    private var daysAndWeekDays: ArrayList<Pair<String, String>> = ArrayList()

    private val calendarEventViewModel: CalendarEventViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDate = LocalDate.now()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding!!.addNewEvent.setOnClickListener {
            val action = CalendarFragmentDirections.actionAddNewEvent()
            view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }

        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidgets()
        setMonthView()

        binding!!.calendarPreviousMonth.setOnClickListener { previousMonthAction(view) }
        binding!!.calendarNextMonth.setOnClickListener { nextMonthAction(view) }


    }

    private fun initWidgets() {
        calendarRecyclerView = binding!!.calendarRecyclerView
        calendarEventRecyclerView = binding!!.calendarEventsRecyclerView

        monthYearText = binding!!.calendarMonthTextView
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)

        val yearMonth = YearMonth.from(selectedDate)
        val numOfDaysInMonth = yearMonth.lengthOfMonth()

        days = ArrayList()
        daysAndWeekDays = ArrayList()
        for (i in 1..numOfDaysInMonth) {
            days.add(i.toString())
            val dateStr = "${i}/${selectedDate.month.value}/${selectedDate.year}"

            val timestamp = DateConverter.DateStringToTimestamp(dateStr, PATTERN, Locale.ENGLISH)
            val date = DateConverter.timestampToLocalDate(timestamp!!)

            daysAndWeekDays.add(Pair(i.toString(), date.dayOfWeek.name.slice(0..2)))
        }

        val calendarAdapter = CalendarAdapter(daysInMonth, selectedDate.dayOfMonth.toString(), this)
        val layoutManager = CustomNonScrollableGridLayout(requireContext(), 7)

        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter

        val calendarEventAdapter = CalendarEventAdapter(
            calendarEventViewModel,
            selectedDate,
            daysAndWeekDays
        )
        calendarEventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        calendarEventRecyclerView.adapter = calendarEventAdapter

        calendarEventViewModel.allEvents.observe(viewLifecycleOwner, Observer { events ->
            calendarEventAdapter.setEvents(events)
        })
        updateScrollPosition(selectedDate.dayOfMonth - 1)
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

    private fun updateScrollPosition(dayNumber: Int) {
        calendarEventRecyclerView.doOnPreDraw {
            (calendarEventRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                dayNumber,
                (it.height * 0.03).toInt()
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(position: Int, value: String) {
        if (value != "") {
            val adapter = calendarRecyclerView.adapter as CalendarAdapter
            adapter.updateCurrDate(value)
            updateScrollPosition(value.toInt() - 1)
        }
    }


    private fun previousMonthAction(view: View) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    private fun nextMonthAction(view: View) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }
}
