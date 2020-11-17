package com.esp.basicapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.content
import kotlinx.android.synthetic.main.activity_main.hello
import kotlinx.android.synthetic.main.activity_main.send
import kotlinx.android.synthetic.main.activity_main.sendto
import kotlinx.android.synthetic.main.activity_main.subject
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        // KTXを導入するとできる
        hello.text = "ハロー"

        send.setOnClickListener {
//            sendMail(
//                sendto.text.toString(),
//                subject.text.toString(),
//                content.text.toString()
//            )
            sendSend(
                subject.text.toString(),
                content.text.toString()
            )
        }
    }

    private fun sendMail(sendTo:String, subject:String, content:String) {
        val intent = Intent(Intent.ACTION_SENDTO)
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(sendTo))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, content)
                selector = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            }
        val chooser = Intent.createChooser(intent, "send app")
        startActivity(chooser)
    }

    private fun sendSend(subject:String, content:String) {
        val intent = Intent(Intent.ACTION_SEND)
            .apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, content)
            }
        val chooser = Intent.createChooser(intent, "send app")
        startActivity(chooser)
    }
}
