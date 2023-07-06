package com.purabmodi.devhacksapp.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.purabmodi.devhacksapp.R
import com.purabmodi.devhacksapp.databinding.FragmentLoadingDialogBinding

class LoadingDialogFragment : DialogFragment() {
    private var _binding: FragmentLoadingDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingDialogBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentLoadingDialogBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(requireActivity())
        isCancelable = false
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.setContentView(R.layout.fragment_loading_dialog)
        dialog.window!!.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_dialog_background))
        dialog.window!!.setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT)
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}