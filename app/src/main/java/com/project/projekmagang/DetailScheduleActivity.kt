package com.project.projekmagang

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.adapter.DetailScheduleAdapter
import com.project.projekmagang.model.MyDetailSchedule

class DetailScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_schedule)

        val day = intent.getStringExtra("day")

        val textView = findViewById<TextView>(R.id.tv_day_detail)
        textView.text = " $day"

        val listCourse= arrayListOf(
            MyDetailSchedule("Matematika"),
            MyDetailSchedule("Bahasa Indonesia"),
            MyDetailSchedule("Bahasa Jawa"),
            MyDetailSchedule("Bahasa Inggris"),
            MyDetailSchedule("Sejarah"),
            MyDetailSchedule("Ekonomi"),
            MyDetailSchedule("Geografi"),
            MyDetailSchedule("Fisika"),
            MyDetailSchedule("Kimia"),
        )
        val adapter=DetailScheduleAdapter(listCourse)
        val layoutManager=GridLayoutManager(this,3)
        val recyclerView=findViewById<RecyclerView>(R.id.rv_detail_schedule)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter

    }
}
