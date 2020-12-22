package com.esp.basicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_spinner_sample.btn_radio
import kotlinx.android.synthetic.main.activity_spinner_sample.btn_snackbar
import kotlinx.android.synthetic.main.activity_spinner_sample.btn_snackbar_action
import kotlinx.android.synthetic.main.activity_spinner_sample.btn_spinner
import kotlinx.android.synthetic.main.activity_spinner_sample.btn_toast
import kotlinx.android.synthetic.main.activity_spinner_sample.rb_cat
import kotlinx.android.synthetic.main.activity_spinner_sample.rb_mobile
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

        btn_radio.setOnClickListener {
            getRadioValue()
        }

        btn_toast.setOnClickListener {
            Toast.makeText(this, "HELLO", Toast.LENGTH_LONG).show()
        }

        btn_snackbar.setOnClickListener {
            Snackbar.make(btn_snackbar, R.string.str_hello, Snackbar.LENGTH_SHORT).show()
        }

        btn_snackbar_action.setOnClickListener {
            val snackbar = Snackbar.make(btn_snackbar, R.string.str_hello, Snackbar.LENGTH_INDEFINITE)

            snackbar.setAction(R.string.btn_close) {
                //ここで処理できる
                Toast.makeText(this, "わかりました", Toast.LENGTH_SHORT).show()
            }

            snackbar.show()
        }
    }

    private fun getSpinnerValue() {
        val selectedString: CharSequence = spinner_string.selectedItem as CharSequence
        Timber.d("spinner_string = $selectedString")

        val selectedPerson: PersonalInfo = spinner_person.selectedItem as PersonalInfo
        Timber.d("spinner_person = ${selectedPerson.name}: ${selectedPerson.age}")
    }

    private fun getRadioValue() {
        // 参照する時
        val animal = if (rb_cat.isChecked) {
            "CAT"
        } else {
            "DOG"
        }
        Timber.d("animal  = $animal")

        // 設定する時
        rb_mobile.isChecked = true
    }
}
