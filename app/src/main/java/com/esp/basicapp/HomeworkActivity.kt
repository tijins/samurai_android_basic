package com.esp.basicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.homework_activity.btn_send
import kotlinx.android.synthetic.main.homework_activity.list
import kotlinx.android.synthetic.main.homework_activity.text
import java.util.Date

class HomeworkActivity : AppCompatActivity(R.layout.homework_activity) {

    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = MessageAdapter(this)
        list.adapter = adapter

        btn_send.setOnClickListener {
            addMessage()
        }
    }

    private fun addMessage() {
        val message = Message(
            text.text.toString(),
            Date(),
            "suzuki"
        )
        adapter.add(message)
    }
}
