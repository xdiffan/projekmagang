package com.project.projekmagang.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.leanback.widget.ImageCardView
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.R
import com.project.projekmagang.model.MyScheduleDay

class ScheduleDayAdapter(
    private var listScheduleDay: List<MyScheduleDay>,
    private val onItemClick: (MyScheduleDay, String) -> Unit
) : RecyclerView.Adapter<ScheduleDayAdapter.ScheduleDayViewHolder>() {

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

        val dayId = scheduleDay.id

        holder.itemView.setOnClickListener {
            onItemClick(scheduleDay, dayId)
        }

        holder.itemView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.scaleX = 1.2f
                view.scaleY = 1.2f
                view.elevation = 3f
            } else {
                view.scaleX = 1.0f
                view.scaleY = 1.0f
                view.elevation = 0f
            }
        }

        holder.itemView.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        if (position > 0) {
                            holder.itemView.requestFocus()
                            (holder.itemView.parent as RecyclerView).findViewHolderForAdapterPosition(position - 1)?.itemView?.requestFocus()
                        }
                        return@setOnKeyListener true
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        if (position < listScheduleDay.size - 1) {
                            holder.itemView.requestFocus()
                            (holder.itemView.parent as RecyclerView).findViewHolderForAdapterPosition(position + 1)?.itemView?.requestFocus()
                        }
                        return@setOnKeyListener true
                    }
                }
            }
            false
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

