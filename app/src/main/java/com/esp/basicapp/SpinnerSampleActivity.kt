package com.esp.basicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_spinner_sample.btn_spinner
import kotlinx.android.synthetic.main.activity_spinner_sample.spinner_person
import kotlinx.android.synthetic.main.activity_spinner_sample.spinner_string
import timber.log.Timber

class SpinnerSampleActivity : AppCompatActivity() {

    private lateinit var personAdapter: ArrayAdapter<PersonalInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner_sample)

        // 文字列のとき
        val adapter = ArrayAdapter.createFromResource(this, R.array.spinner_item, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_string.adapter = adapter

        val list = arrayOf(
            PersonalInfo("Yamada", 1),
            PersonalInfo("Tanaka", 2)
        )

        // Objectを表示する
        personAdapter = object : ArrayAdapter<PersonalInfo>(this, android.R.layout.simple_spinner_item, list) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val sample = getItem(position)

                val view = convertView
                    ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false)

                val text1 = view.findViewById<TextView>(android.R.id.text1)
                text1.text = sample?.name

                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val sample = getItem(position)

                val view = convertView
                    ?: LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

                val text1 = view.findViewById<TextView>(android.R.id.text1)
                text1.text = sample?.name

                return view
            }
        }
        spinner_person.adapter = personAdapter

        btn_spinner.setOnClickListener {
            getSpinnerValue()
        }
    }

    private fun getSpinnerValue() {
        val selectedString: CharSequence = spinner_string.selectedItem as CharSequence
        Timber.d("spinner_string = $selectedString")

        val selectedPerson: PersonalInfo = spinner_person.selectedItem as PersonalInfo
        Timber.d("spinner_person = ${selectedPerson.name}: ${selectedPerson.age}")
    }
}
