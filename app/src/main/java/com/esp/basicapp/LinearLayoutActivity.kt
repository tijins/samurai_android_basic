package com.esp.basicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_linear_layout.area_personal_info
import kotlinx.android.synthetic.main.activity_linear_layout.btn_add
import kotlinx.android.synthetic.main.item_personal_info.view.txt_age
import kotlinx.android.synthetic.main.item_personal_info.view.txt_name

class LinearLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        btn_add.setOnClickListener {
            addPersonalInfo()
        }
    }

    private fun addPersonalInfo() {
        val inflater = LayoutInflater.from(this)

        val view = inflater.inflate(R.layout.item_personal_info, area_personal_info, false)
        view.txt_name.text = "スズキ"
        view.txt_age.text = 1.toString()

        area_personal_info.addView(view)
    }
}
