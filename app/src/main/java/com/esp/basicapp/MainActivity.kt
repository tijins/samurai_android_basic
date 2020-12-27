package com.esp.basicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // 2nd Activityを起動するためのイベント
        btn_second.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)

            if(content.text.isNotBlank()){
                intent.putExtra(SecondActivity.EXTRA_CONTENT, content.text.toString())
            }

            startActivity(intent)
        }
    }
}
