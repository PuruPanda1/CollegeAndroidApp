package com.purabmodi.devhacksapp.ui.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.databinding.FragmentLostieBinding

class LostieFragment : Fragment() {
    private var _binding: FragmentLostieBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageUri:Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLostieBinding.inflate(layoutInflater, container, false)

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.findLostItemBtn.setOnClickListener {
            findNavController().navigate(R.id.action_lostieFragment_to_inventoryFragment)
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