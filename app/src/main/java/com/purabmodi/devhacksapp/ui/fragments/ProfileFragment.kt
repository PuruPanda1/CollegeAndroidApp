package com.purabmodi.devhacksapp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.data.models.Item
import com.purabmodi.devhacksapp.data.models.User
import com.purabmodi.devhacksapp.databinding.FragmentProfileBinding
import com.purabmodi.devhacksapp.utils.SharedPref
import com.purabmodi.devhacksapp.utils.utils

class ProfileFragment : Fragment() {

    private var _binding:FragmentProfileBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)


        val usn = SharedPref(requireContext()).getUserName()
        val dbRef = Firebase.database.getReference("users")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    val user = data.getValue<User>()
                    if(user!=null && user.usn == usn){
                        binding.profileUserName.text = user.name
                        binding.usn.text = user.usn
                        binding.course.text = user.group
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("CHECKINGUID", "Failed")
            }
        })

        binding.applyToHostelBtn.setOnClickListener {

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}