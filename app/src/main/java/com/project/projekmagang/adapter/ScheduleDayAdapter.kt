package com.project.projekmagang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.R
import com.project.projekmagang.model.MyScheduleDay

class ScheduleDayAdapter(
    private val listScheduleDay: ArrayList<MyScheduleDay>,
    private val onItemClick: (MyScheduleDay) -> Unit
) : RecyclerView.Adapter<ScheduleDayAdapter.ScheduleDayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_schedule, parent, false)
        return ScheduleDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleDayViewHolder, position: Int) {
        val scheduleDay = listScheduleDay[position]
        holder.bind(scheduleDay)
        holder.itemView.findViewById<LinearLayout>(R.id.ll_day_schedule).setOnClickListener {
            onItemClick(scheduleDay)
        }
    }

    override fun getItemCount(): Int = listScheduleDay.size

    class ScheduleDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(scheduleDay: MyScheduleDay) {
            val tvDay = itemView.findViewById<TextView>(R.id.tv_day)
            tvDay.text = scheduleDay.day
        }
    }
}
