package com.myapps.projectx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myapps.projectx.data.profile.User
import com.myapps.projectx.data.profile.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
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

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val name = view.findViewById<TextView>(R.id.userName)
        val email = view.findViewById<TextView>(R.id.userEmail)



        userViewModel.getUser.observe(viewLifecycleOwner, Observer { user ->
            if (!user.isEmpty()){
                val fName = user[0].firstName
                val lName = user[0].lastName
                val fullName = "$fName $lName"
                name.text = fullName
                email.text = user[0].email
            } else{
                userViewModel.addUser(User(0, "user1", "user1", "user1@hiof.no"))
                Toast.makeText(requireContext(), "Empty DB", Toast.LENGTH_SHORT).show()
            }

//            Log.d("Temp", user[0].firstName)

        })

//        temp.setOnClickListener {
//
////            val user = User(0, "John", "Snow", "bastard@hiof.no")
////            userViewModel.addUser(user)
//            userViewModel.getUser.observe(viewLifecycleOwner, Observer { user ->
//                Log.d("Temp", user[0].firstName)
//            })
//            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
//
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
//         * @return A new instance of fragment ProfileFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ProfileFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}