package com.esp.basicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_personal_info.view.txt_age
import kotlinx.android.synthetic.main.item_personal_info.view.txt_name

class PersonalInfoAdapter(private val list: ArrayList<PersonalInfo>) : RecyclerView.Adapter<PersonalInfoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_personal_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.txt_name.text = item.name
        holder.itemView.txt_age.text = item.age.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
