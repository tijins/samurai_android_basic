package com.esp.basicapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.btn_alert
import kotlinx.android.synthetic.main.activity_main.btn_beep
import kotlinx.android.synthetic.main.activity_main.btn_normal

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CH_NORMAL = "normal"
        private const val CH_ALERT = "alert"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationCh()
        }

        btn_normal.setOnClickListener {
            notifyNormal()
        }
        btn_alert.setOnClickListener {
            notifyAlert()
        }
        btn_beep.setOnClickListener {
            beep()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationCh() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(
            NotificationChannel(CH_NORMAL, "お知らせ", NotificationManager.IMPORTANCE_LOW).apply {
                enableVibration(false)
            }
        )
        manager.createNotificationChannel(
            NotificationChannel(CH_ALERT, "重要通知", NotificationManager.IMPORTANCE_HIGH).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 50, 100, 50) //100msモーターを動作、50ms停止、100msモーターを動作、50ms停止
            }
        )
    }

    private fun notifyNormal() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, CH_NORMAL)
            } else {
                Notification.Builder(this)
            }.apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentTitle("通常の通知")
                setContentText("これは通常の通知です")
                setContentIntent(pendingIntent)
            }.build()

        manager.notify(0, notification)
    }

    private fun notifyAlert() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Notification.Builder(this, CH_ALERT)
            } else {
                Notification.Builder(this)
            }.apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentTitle("重要な通知")
                setContentText("これは重要な通知です")
                setContentIntent(pendingIntent)
            }.build()

        manager.notify(0, notification)
    }

    private fun beep() {
        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME)
        // 種類、長さms
        toneGenerator.startTone(ToneGenerator.TONE_SUP_ERROR, 1500)
    }
}
