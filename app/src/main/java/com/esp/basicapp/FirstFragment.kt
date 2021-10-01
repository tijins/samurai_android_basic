package com.esp.basicapp

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.esp.basicapp.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        navController = binding.root.findNavController()

        binding.next.setOnClickListener {
            navController.navigate(
                R.id.action_firstFragment_to_secondFragment,
                bundleOf("title" to "title from args")
            )
        }
    }
}
