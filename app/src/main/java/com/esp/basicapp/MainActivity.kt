package com.esp.basicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQ_SELECT_AUDIO = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate")

        //Audioを選択
        val btnSelectAudio = findViewById<Button>(R.id.select_audio)
        btnSelectAudio.setOnClickListener {
            selectAudio()
        }
    }

    private fun selectAudio() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "audio/*"
        startActivityForResult(intent, REQ_SELECT_AUDIO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            // 選択がキャンセルされた時
            return
        }

        // リクエストコードが複数ある場合は、ここで分岐する（1つなら不要）
        when (requestCode) {
            REQ_SELECT_AUDIO -> {
                Timber.d("${data?.data}")
                Timber.d("${data?.data?.path}")
            }
        }
    }
}
