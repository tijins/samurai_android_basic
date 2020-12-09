package com.esp.basicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.btn_constraint
import kotlinx.android.synthetic.main.activity_main.btn_homework
import kotlinx.android.synthetic.main.activity_main.btn_spinner
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        val btnLinear = findViewById<Button>(R.id.btn_linear)
        btnLinear.setOnClickListener {
            startActivity(Intent(this, LinearLayoutActivity::class.java))
        }

        btn_constraint.setOnClickListener {
            startActivity(Intent(this, ConstraintLayoutActivity::class.java))
        }

        btn_spinner.setOnClickListener {
            startActivity(Intent(this, SpinnerSampleActivity::class.java))
        }

        btn_homework.setOnClickListener {
            startActivity(Intent(this, HomeworkActivity::class.java))
        }
    }
}
