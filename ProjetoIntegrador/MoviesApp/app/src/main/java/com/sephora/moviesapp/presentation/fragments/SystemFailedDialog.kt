package com.sephora.moviesapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sephora.moviesapp.R
import com.sephora.moviesapp.databinding.DialogSystemFailedBinding

class SystemFailedDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_system_failed, container, false)
    }
    private lateinit var binding : DialogSystemFailedBinding
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DialogSystemFailedBinding.bind(view)
        binding.apply {
            btnTryAgain.setOnClickListener {
                requireActivity().onBackPressed()
            }
            btnClose.setOnClickListener{
                requireActivity().onBackPressed()

            }
        }

    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }


}