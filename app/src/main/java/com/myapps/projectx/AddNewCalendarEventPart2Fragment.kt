package com.myapps.projectx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.myapps.projectx.data.AppDatabase
import com.myapps.projectx.data.calendar_events.CalendarEvent
import com.myapps.projectx.data.calendar_events.CalendarEventViewModel
import com.myapps.projectx.databinding.FragmentAddNewCalendarEventPart2Binding
import com.myapps.projectx.libs.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddNewCalendarEventPart2Fragment : Fragment() {

    private var _binding: FragmentAddNewCalendarEventPart2Binding? = null
    private val binding get() = _binding!!

    private val args: AddNewCalendarEventPart2FragmentArgs by navArgs()
    private val calendarEventViewModel: CalendarEventViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddNewCalendarEventPart2Binding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date = "${args.day}/${args.month}/${args.year}"
        binding.eventDate.text = date

        val timestamp = DateConverter.DateStringToTimestamp(date, "dd/MM/yyyy", Locale.ENGLISH)

        binding.button.setOnClickListener {
            println("Text: ${binding.eventText.text}")
            val event = CalendarEvent(
                0,
                binding.eventText.text.toString(),
                0,
                0,
                "#000",
                timestamp!!
            )

            calendarEventViewModel.addEvent(event)

            val action = AddNewCalendarEventPart2FragmentDirections.actionGoBackToCalendar()
            Navigation.findNavController(view).navigate(action)
        }
    }

}