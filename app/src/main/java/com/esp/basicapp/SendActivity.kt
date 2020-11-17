package com.esp.basicapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_send.content
import kotlinx.android.synthetic.main.activity_send.subject
import timber.log.Timber

class SendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        Timber.d(intent.action)

        // IntentのActionで処理を分岐できる
        if(intent.action == Intent.ACTION_SEND){
            subject.text = intent.getStringExtra(Intent.EXTRA_SUBJECT)
            content.text = intent.getStringExtra(Intent.EXTRA_TEXT)
        }
    }
}
