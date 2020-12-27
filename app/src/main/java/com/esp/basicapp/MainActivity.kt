package com.esp.basicapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        send.setOnClickListener {
            sendMail(
                sendto.text.toString(),
                subject.text.toString(),
                content.text.toString()
            )
        }
    }

    private fun sendMail(mail:String, subject:String, content:String) {
        val intent = Intent(Intent.ACTION_SEND)
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(mail))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, content)
            }
        val chooser = Intent.createChooser(intent, "send app")
        startActivity(chooser)
    }
}
