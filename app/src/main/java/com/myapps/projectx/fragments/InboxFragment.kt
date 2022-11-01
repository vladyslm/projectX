package com.myapps.projectx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.projectx.adapters.InboxAdapter
import com.myapps.projectx.data.inbox.InboxMessage
import com.myapps.projectx.data.inbox.InboxMessageViewModel
import com.myapps.projectx.databinding.FragmentInboxBinding


class InboxFragment : Fragment() {

    private var _binding: FragmentInboxBinding? = null
    private val binding get() = _binding!!

    private lateinit var inboxViewModel: InboxMessageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInboxBinding.inflate(inflater, container, false)
        inboxViewModel = ViewModelProvider(this).get(InboxMessageViewModel::class.java)

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = InboxAdapter(inboxViewModel)

        binding.inboxRecyclerView.adapter = adapter
        binding.inboxRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        inboxViewModel.readAllMessages.observe(viewLifecycleOwner, Observer { message ->
            if (message.isEmpty()) {
                inboxViewModel.addInboxMessage(
                    InboxMessage(
                        0,
                        "message 1",
                        "user123",
                        1667331264516,
                        false
                    )
                )
            }

            adapter.setData(message)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}