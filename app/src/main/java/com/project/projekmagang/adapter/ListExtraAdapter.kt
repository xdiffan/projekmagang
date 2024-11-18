package com.project.projekmagang.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.projekmagang.R
import com.project.projekmagang.model.Extra

class ListExtraAdapter(
    private val extras: List<Extra>,
    private val onItemClick: (Extra) -> Unit
) : RecyclerView.Adapter<ListExtraAdapter.ExtraViewHolder>() {

    inner class ExtraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_extra)
        val imageView: ImageView = view.findViewById(R.id.iv_extra)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_extra, parent, false)
        return ExtraViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExtraViewHolder, position: Int) {
        val extra = extras[position]
        holder.nameTextView.text = extra.name
        Glide.with(holder.itemView.context)
            .load(extra.image)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClick(extra)
        }

        holder.itemView.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.scaleX = 1.2f
                view.scaleY = 1.2f
                view.elevation = 4f
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
                        if (position < extras.size - 1) {
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

    override fun getItemCount(): Int = extras.size
}