package com.myapps.projectx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.myapps.projectx.data.calendar_events.CalendarEvent
import com.myapps.projectx.data.calendar_events.CalendarEventViewModel
import com.myapps.projectx.databinding.FragmentAddNewCalendarEventPart2Binding
import com.myapps.projectx.libs.DateConverter
import java.text.ParseException
import java.util.*

const val EMPTY_EVENT_TEXT_MESSAGE = "Event field can't be empty"
const val INVALID_TIME_VALUE_MESSAGE = "Provide a valid time value"
const val TIME_PATTERN_SHORT = "dd/MM/yyyy"
const val TIME_PATTERN_LONG = "dd/MM/yyyy HH:mm:ss"

class AddNewCalendarEventPart2Fragment : Fragment() {

    private var _binding: FragmentAddNewCalendarEventPart2Binding? = null
    private val binding get() = _binding!!

    private val args: AddNewCalendarEventPart2FragmentArgs by navArgs()
    private val calendarEventViewModel: CalendarEventViewModel by activityViewModels()
    private var eventColor: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNewCalendarEventPart2Binding.inflate(inflater, container, false)

        setSpinner()

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = "${args.day}/${args.month}/${args.year}"
        binding.eventDate.text = date

        binding.button.setOnClickListener {

            try {
                val event = validateForm()
                calendarEventViewModel.addEvent(event)

                val action = AddNewCalendarEventPart2FragmentDirections.actionGoBackToCalendar()
                Navigation.findNavController(view).navigate(action)

            } catch (e: java.lang.IllegalArgumentException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                println(e.message)
            } catch (e: ParseException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): CalendarEvent {
        val eventText = binding.eventText.text.toString()
        if (eventText == "") {
            throw IllegalArgumentException(EMPTY_EVENT_TEXT_MESSAGE)
        }

        val timeFrom = validateTimeValue(binding.eventTimeFrom.text.toString())
        val timeTo =
            if (timeFrom == 0L) 0
            else validateTimeValue(binding.eventTimeTo.text.toString())

        val date = "${args.day}/${args.month}/${args.year}"
        binding.eventDate.text = date

        val timestamp =
            DateConverter.DateStringToTimestamp(date, TIME_PATTERN_SHORT, Locale.ENGLISH)

        return CalendarEvent(
            0,
            eventText,
            timeFrom,
            timeTo,
            mapColor(eventColor),
            timestamp!!
        )
    }

    private fun validateTimeValue(time: String): Long {
        if (time == "") {
            return 0
        }
        try {
            val split = time.split(":")
            if (split.size < 2) {
                throw IllegalArgumentException(INVALID_TIME_VALUE_MESSAGE)
            }
            val dateAndTime = "${args.day}/${args.month}/${args.year} ${split[0]}:${split[1]}:00"
            return DateConverter.DateStringToTimestamp(
                dateAndTime,
                TIME_PATTERN_LONG,
                Locale.ENGLISH
            )!!
        } catch (e: ParseException) {
            throw IllegalArgumentException(INVALID_TIME_VALUE_MESSAGE)
        }
    }

    private fun setSpinner() {
        val colors = resources.getStringArray(R.array.event_colors)
        val colorSpinnerAdapter = ArrayAdapter(
            requireContext(),
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            colors
        )
        binding.eventColorSpinner.adapter = colorSpinnerAdapter
        binding.eventColorSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val selected = parent?.getItemAtPosition(pos)
                println(selected)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                eventColor = colors[0]
            }

        }
    }

    companion object {
        private val colorMap = mapOf(
            "Green" to "#24A129",
            "Purple" to "#9724A1",
            "Orange" to "#FF9B11",
            "Blue" to "#2440A1"
        )

        fun mapColor(color: String): String {
            return colorMap[color] ?: return colorMap["Green"]!!
        }
    }

}