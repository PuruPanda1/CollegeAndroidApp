package com.purabmodi.devhacksapp.ui.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.databinding.FragmentLostieBinding

class LostieFragment : Fragment() {
    private var _binding: FragmentLostieBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUri:Uri
    private lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLostieBinding.inflate(layoutInflater, container, false)

//        SharedPref(requireContext()).setUserName("1ST22CS415")

        setListeners()

        return binding.root
    }



    private fun setListeners() {
        binding.findLostItemBtn.setOnClickListener {
            findNavController().navigate(R.id.action_lostieFragment_to_inventoryFragment
            )
        }
        binding.scanButton.setOnClickListener {
            findNavController().navigate(R.id.action_lostieFragment_to_lostItemDetailsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}