package com.esp.basicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_linear_layout.btn_add
import kotlinx.android.synthetic.main.activity_linear_layout.list

class LinearLayoutActivity : AppCompatActivity() {

    private val items = ArrayList<PersonalInfo>()
    private val adapter = PersonalInfoAdapter(items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        btn_add.setOnClickListener {
            addPersonalInfo()
        }
    }

    private fun addPersonalInfo() {
        val info = PersonalInfo("${items.size}さん", items.size)
        items.add(info)
        adapter.notifyDataSetChanged()
    }
}
