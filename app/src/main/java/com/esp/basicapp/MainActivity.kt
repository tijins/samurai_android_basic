package com.esp.basicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
    }
}
