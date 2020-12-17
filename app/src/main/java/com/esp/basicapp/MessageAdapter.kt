package com.esp.basicapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_message.view.date
import kotlinx.android.synthetic.main.item_message.view.sender_name
import kotlinx.android.synthetic.main.item_message.view.text
import java.text.DateFormat

class MessageAdapter(
    context: Context
) : ArrayAdapter<Message>(context, R.layout.item_message) {

    private val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)

        val message = getItem(position) ?: return view

        view.apply {
            sender_name.text = message.sender
            date.text = dateFormat.format(message.date)
            text.text = message.text
        }

        return view
    }
}
