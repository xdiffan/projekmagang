package com.project.projekmagang.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.R
import com.project.projekmagang.model.MyScheduleDay

class ResultDayScheduleAdapter(
    private var listResultSchedule: List<MyScheduleDay>,
    private val onItemClick: (MyScheduleDay, String) -> Unit
) : RecyclerView.Adapter<ResultDayScheduleAdapter.ResultDayScheduleViewHolder>() {

    fun updateData(newResultScheduleDays: List<MyScheduleDay>) {
        listResultSchedule = newResultScheduleDays
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultDayScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_day_schedule, parent, false)
        return ResultDayScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultDayScheduleViewHolder, position: Int) {
        val resultScheduleDay = listResultSchedule[position]
        holder.bind(resultScheduleDay)

        val resultDayId = resultScheduleDay.id
        holder.itemView.setOnClickListener {
            onItemClick(resultScheduleDay, resultDayId)
        }

        holder.itemView.setOnFocusChangeListener() { view, hasFocus ->

            val btnResultDay=view.findViewById<Button>(R.id.btn_result_day)
            if (hasFocus) {
                btnResultDay.scaleX = 1.3f
                btnResultDay.scaleY = 1.3f
                btnResultDay.elevation = 3.3f
            } else {
                btnResultDay.scaleX = 1.0f
                btnResultDay.scaleY = 1.0f
                btnResultDay.elevation = 0f
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
                        if (position < listResultSchedule.size - 1) {
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

    override fun getItemCount(): Int = listResultSchedule.size

    class ResultDayScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resultScheduleDay: MyScheduleDay) {
            val tvResultDay = itemView.findViewById<TextView>(R.id.tv_result_day)
            tvResultDay.text = resultScheduleDay.day
            itemView.isFocusable = true
            itemView.isFocusableInTouchMode = true
        }
    }
}