package com.esp.basicapp

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.btn_play
import kotlinx.android.synthetic.main.activity_main.btn_select
import kotlinx.android.synthetic.main.activity_main.btn_stop
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    companion object {
        private const val REQ_SELECT_AUDIO = 1
    }

    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_select.setOnClickListener {
            selectAudio()
        }
        btn_play.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        }
        btn_stop.setOnClickListener {
            mediaPlayer.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun selectAudio() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
        }
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
                Timber.d("Intent.Data : ${data?.data}")
                data?.data?.let { uri ->
                    setMediaFile(uri)
                }
            }
        }
    }

    private fun setMediaFile(uri: Uri) {
        mediaPlayer.setDataSource(this, uri)
        mediaPlayer.prepare()
    }
}
