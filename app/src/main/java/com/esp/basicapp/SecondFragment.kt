package com.esp.basicapp

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.esp.basicapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view) ?: return

        binding.fragmentTitle.text = arguments?.getString("title")

        binding.back.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }
}
