package com.esp.basicapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_spinner_sample.spinner

class Sample(var name: String, var age: Int)

class SpinnerSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner_sample)

//        val list = arrayOf(
//            Sample("Yamada", 1),
//            Sample("Tanaka", 2)
//        )
//        val adapter = object : ArrayAdapter<Sample>(this, android.R.layout.simple_spinner_item, list) {
//            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//                val sample = getItem(position)
//
//                val view = super.getView(position, convertView, parent)
//                val text1 = view.findViewById<TextView>(android.R.id.text1)
//                text1.text = sample?.name
//
//                return view
//            }
//
//            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
//                val sample = getItem(position)
//
//                val view = super.getDropDownView(position, convertView, parent)
//                val text1 = view.findViewById<TextView>(android.R.id.text1)
//                text1.text = sample?.name
//
//                return view
//            }
//        }
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapter = ArrayAdapter.createFromResource(this, R.array.spinner_item, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter
    }
}
