package com.purabmodi.devhacksapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.adapter.InventoryAdapter
import com.purabmodi.devhacksapp.data.models.Item
import com.purabmodi.devhacksapp.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInventoryBinding.inflate(layoutInflater, container, false)

        init()

        return binding.root
    }

    private fun init() {

        var dataList = ArrayList<Item>()

        val inventoryAdapter = InventoryAdapter(
            onClick = {
                onclick()
            })
        binding.inventoryRC.adapter = inventoryAdapter
        binding.inventoryRC.layoutManager = LinearLayoutManager(requireContext())

        val dbRef = Firebase.database.getReference("items")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    val item = data.getValue<Item>()
                    Log.d("CHECKINGUID", "onDataChange: ${item!!.uid}")
                    dataList.add(item!!)
                }
                inventoryAdapter.setItems(dataList)
                binding.inventoryAnimation.isVisible=false
                binding.inventoryRC.isVisible = true

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        })



    }

    fun onclick() {
        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
    }
}