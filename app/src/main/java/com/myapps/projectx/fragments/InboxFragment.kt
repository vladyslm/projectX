package com.myapps.projectx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapps.projectx.adapters.InboxAdapter
import com.myapps.projectx.R
import com.myapps.projectx.data.inbox.InboxMessageViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InboxFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InboxFragment : Fragment() {
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

//    val  messages = arrayListOf<String>()

    private lateinit var inboxViewModel: InboxMessageViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inbox, container, false)

//        val messages = resources.getStringArray(R.array.messages).toList()
//        val from = resources.getStringArray(R.array.from_inbox).toList()


        inboxViewModel = ViewModelProvider(this).get(InboxMessageViewModel::class.java)
        val adapter = InboxAdapter(inboxViewModel)

        recyclerView = view.findViewById(R.id.inboxRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        inboxViewModel.readAllMessages.observe(viewLifecycleOwner, Observer { message ->
            adapter.setData(message)
        })

//        adapter.setData(messages)

//        val addInbox = view.findViewById<Button>(R.id.addInbox)

//        addInbox.setOnClickListener {
//            val temp = inboxViewModel.addInboxMessage(InboxMessage(0, "lorem lorem 1", "barack obama", "10/13/2022", false))
//            val msgs = inboxViewModel.readAllMessages
////            Log.d("temp", msgs.value.toString())
//        }

        return view
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment InboxFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            InboxFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}