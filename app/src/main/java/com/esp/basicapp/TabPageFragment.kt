package com.esp.basicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class TabPageFragment : Fragment() {
    companion object {
        private const val ARG_NAME = "name"

        fun newInstance(name: String): Fragment {
            return TabPageFragment().apply {
                arguments = bundleOf(
                    ARG_NAME to name
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.page_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.also {
            val textView = view.findViewById<TextView>(R.id.text1)
            textView?.text = it.getString(ARG_NAME)
        }
    }
}
