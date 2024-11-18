package com.project.projekmagang.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.R
import com.project.projekmagang.model.MyDetailSchedule
import com.project.projekmagang.model.MyScheduleDay

class DetailScheduleAdapter(
    private val listDetailSchedule: ArrayList<MyDetailSchedule>,
    private val onItemClick: (MyDetailSchedule) -> Unit
) : RecyclerView.Adapter<DetailScheduleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val course: TextView = itemView.findViewById(R.id.tv_course)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_detail_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listDetailSchedule.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listDetailSchedule[position]
        holder.course.text = item.course

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }

        holder.itemView.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.scaleX = 1.4f
                view.scaleY = 1.4f
                view.elevation = 5f
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
                            (holder.itemView.parent as RecyclerView)
                                .findViewHolderForAdapterPosition(position - 1)?.itemView?.requestFocus()
                        }
                        return@setOnKeyListener true
                    }
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        if (position < listDetailSchedule.size - 1) {
                            (holder.itemView.parent as RecyclerView)
                                .findViewHolderForAdapterPosition(position + 1)?.itemView?.requestFocus()
                        }
                        return@setOnKeyListener true
                    }
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        val spanCount = 3
                        if (position - spanCount >= 0) {
                            (holder.itemView.parent as RecyclerView)
                                .findViewHolderForAdapterPosition(position - spanCount)?.itemView?.requestFocus()
                        }
                        return@setOnKeyListener true
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        val spanCount = 3
                        if (position + spanCount < listDetailSchedule.size) {
                            (holder.itemView.parent as RecyclerView)
                                .findViewHolderForAdapterPosition(position + spanCount)?.itemView?.requestFocus()
                        }
                        return@setOnKeyListener true
                    }
                }
            }
            false
        }
    }
}