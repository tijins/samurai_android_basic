package com.esp.basicapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.esp.basicapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private lateinit var binding: FragmentSecondBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
        navController = binding.root.findNavController()

        binding.fragmentTitle.text = arguments?.getString("title")

        binding.chart.setOnClickListener {
            navController.navigate(R.id.action_secondFragment_to_chartFragment)
        }
        binding.barChart.setOnClickListener {
            navController.navigate(R.id.action_secondFragment_to_barChartFragment)
        }
    }
}
