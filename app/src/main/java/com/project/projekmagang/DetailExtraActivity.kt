package com.project.projekmagang

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailExtraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_extra)

        val imageUrl = intent.getStringExtra("extra_image")
        val detailText = intent.getStringExtra("extra_detail")

        val imageView = findViewById<ImageView>(R.id.iv_detail_extra)
        val textView = findViewById<TextView>(R.id.tv_detail_extra)

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        textView.text = detailText
    }
}
