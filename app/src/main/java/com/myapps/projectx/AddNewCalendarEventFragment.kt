package com.myapps.projectx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.adapters.CalendarAdapter
import com.myapps.projectx.customLayouts.CustomNonScrollableGridLayout
import com.myapps.projectx.data.calendar_events.CalendarEventViewModel
import com.myapps.projectx.databinding.FragmentAddNewCalendarEventBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class AddNewCalendarEventFragment : Fragment(), CalendarAdapter.OnItemListener {
    private var _binding: FragmentAddNewCalendarEventBinding? = null
    private val binding get() = _binding

    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: LocalDate

    private var days: ArrayList<String> = ArrayList()

    private lateinit var calendarEventViewModel: CalendarEventViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedDate = LocalDate.now()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNewCalendarEventBinding.inflate(inflater, container, false)

        calendarEventViewModel = ViewModelProvider(this).get(CalendarEventViewModel::class.java)

        val yearMonth = YearMonth.from(selectedDate)
        val daysInMonth = yearMonth.lengthOfMonth()


        for (i in 1..daysInMonth) {
            days.add(i.toString())
        }


        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidgets()
        setMonthView()

        binding!!.calendarPreviousMonth.setOnClickListener { previousMonthAction(view) }
        binding!!.calendarNextMonth.setOnClickListener { nextMonthAction(view) }
        binding!!.nextButton.setOnClickListener { nextPart(view) }
    }


    private fun initWidgets() {
        calendarRecyclerView = binding!!.calendarRecyclerView

        monthYearText = binding!!.calendarMonthTextView
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)

        val daysInMonth = daysInMonthArray(selectedDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, selectedDate.dayOfMonth.toString(), this)
        val layoutManager = CustomNonScrollableGridLayout(requireContext(), 7)

        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
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

    private fun previousMonthAction(view: View) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    private fun nextMonthAction(view: View) {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    private fun nextPart(view: View) {
        val action = AddNewCalendarEventFragmentDirections.actionGoToSecondPart(
            selectedDate.dayOfMonth.toString(),
            selectedDate.month.value.toString(),
            selectedDate.year.toString()

        )
        Navigation.findNavController(view).navigate(action)
    }

    override fun onItemClick(position: Int, value: String) {
        if (value != "") {
            selectedDate = selectedDate.withDayOfMonth(value.toInt())
            val adapter = calendarRecyclerView.adapter as CalendarAdapter
            adapter.updateCurrDate(value)
        }
    }
}