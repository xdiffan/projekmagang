package com.project.projekmagang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.R
import com.project.projekmagang.model.MyDetailSchedule

class DetailResultScheduleAdapter(
    private val listDetailSchedule: ArrayList<MyDetailSchedule>
) : RecyclerView.Adapter<DetailResultScheduleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val course = itemView.findViewById<TextView>(R.id.tv_course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_detail_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listDetailSchedule.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listDetailSchedule[position]
        holder.course.text = item.course
    }

    fun updateData(newDetailSchedules: List<MyDetailSchedule>) {
        listDetailSchedule.clear()
        listDetailSchedule.addAll(newDetailSchedules)
        notifyDataSetChanged()
    }
}
