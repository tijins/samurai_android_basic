package com.esp.basicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber

class MainActivity : AppCompatActivity()
,SampleFragment.SampleFragmentListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        val fragment = supportFragmentManager.findFragmentByTag(SampleFragment::class.java.simpleName)
                ?: SampleFragment.newInstance("suzuki")
        supportFragmentManager.beginTransaction().replace(R.id.view_root, fragment, SampleFragment::class.java.simpleName)
                .commit()
    }

    override fun onSave(name: String) {
        Timber.d(name)
    }
}
