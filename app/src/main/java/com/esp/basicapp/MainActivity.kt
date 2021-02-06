package com.esp.basicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        val fragment = supportFragmentManager.findFragmentByTag(TabFragment::class.java.simpleName)
            ?: TabFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, fragment, TabFragment::class.java.simpleName)
            .commit()
    }
}
