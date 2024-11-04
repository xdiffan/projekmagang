package com.project.projekmagang.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.leanback.widget.ImageCardView
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.R
import com.project.projekmagang.model.MyScheduleDay

class ScheduleDayAdapter(
    private var listScheduleDay: List<MyScheduleDay>,
    private val onItemClick: (MyScheduleDay, String) -> Unit // Tambahkan ID dokumen
) : RecyclerView.Adapter<ScheduleDayAdapter.ScheduleDayViewHolder>() {

    // Method to update data
    fun updateData(newScheduleDays: List<MyScheduleDay>) {
        listScheduleDay = newScheduleDays
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_schedule, parent, false)
        return ScheduleDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleDayViewHolder, position: Int) {
        val scheduleDay = listScheduleDay[position]
        holder.bind(scheduleDay)

        val dayId = scheduleDay.id // Ambil ID dokumen dari objek MyScheduleDay
        holder.itemView.findViewById<LinearLayout>(R.id.ll_day_schedule).setOnClickListener {
            onItemClick(scheduleDay, dayId)
        }
    }

    override fun getItemCount(): Int = listScheduleDay.size

    class ScheduleDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(scheduleDay: MyScheduleDay) {
            val tvDay = itemView.findViewById<TextView>(R.id.tv_day)
            tvDay.text = scheduleDay.day
            itemView.isFocusable = true
            itemView.isFocusableInTouchMode = true
        }
    }
}

