package com.esp.basicapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_linear_layout.btn_add
import kotlinx.android.synthetic.main.activity_linear_layout.list
import kotlinx.android.synthetic.main.item_personal_info.view.txt_age
import kotlinx.android.synthetic.main.item_personal_info.view.txt_name

class LinearLayoutActivity : AppCompatActivity() {

    private class PersonalInfoAdapter(context: Context) : ArrayAdapter<PersonalInfo>(context, R.layout.item_personal_info) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val view =
                convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.item_personal_info, parent, false)

            val item = getItem(position)
            view.txt_name.text = item?.name
            view.txt_age.text = item?.age.toString()

            return view
        }
    }

    private lateinit var adapter: PersonalInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        adapter = PersonalInfoAdapter(this)
        list.adapter = adapter

//  きれいな書き方
//        list.adapter = PersonalInfoAdapter(this).also {
//            adapter = it
//        }

        btn_add.setOnClickListener {
            addPersonalInfo()
        }
    }

    private fun addPersonalInfo() {
        val info = PersonalInfo("${adapter.count}さん", adapter.count)
        adapter.add(info)
    }
}
