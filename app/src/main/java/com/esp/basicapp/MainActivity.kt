package com.esp.basicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esp.basicapp.power.PowerService
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // 電源監視用のサービスを起動する
        val intent = Intent(this, PowerService::class.java)
        startService(intent)
    }
}
