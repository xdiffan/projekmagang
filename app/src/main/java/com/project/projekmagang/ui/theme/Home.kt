package com.project.projekmagang.ui.theme

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.DetailScheduleActivity
import com.project.projekmagang.R
import com.project.projekmagang.adapter.ScheduleDayAdapter
import com.project.projekmagang.model.MyScheduleDay

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listScheduleDay = arrayListOf(
            MyScheduleDay("Senin"),
            MyScheduleDay("Selasa"),
            MyScheduleDay("Rabu"),
            MyScheduleDay("Kamis"),
            MyScheduleDay("Jumat"),
        )

        val adapter = ScheduleDayAdapter(listScheduleDay) { scheduleDay ->
            val intent = Intent(this, DetailScheduleActivity::class.java)
            intent.putExtra("day", scheduleDay.day)
            startActivity(intent)
        }

        val layoutManager = GridLayoutManager(this, 3)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_schedule)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}
