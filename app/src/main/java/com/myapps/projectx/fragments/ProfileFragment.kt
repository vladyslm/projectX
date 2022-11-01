package com.myapps.projectx.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapps.projectx.data.profile.User
import com.myapps.projectx.data.profile.UserViewModel
import com.myapps.projectx.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getUser.observe(viewLifecycleOwner, Observer { user ->
            if (user.isNotEmpty()) {
                val fName = user[0].firstName
                val lName = user[0].lastName
                val fullName = "$fName $lName"
                binding!!.userName.text = fullName
                binding!!.userEmail.text = user[0].email
            }
        })

    }
}