package com.project.projekmagang

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.projekmagang.adapter.DetailResultScheduleAdapter
import com.project.projekmagang.model.MyDetailSchedule

class DetailResultSchedule : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: DetailResultScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_result_schedule)

        firestore = FirebaseFirestore.getInstance()

        val day=intent.getStringExtra("day")

        val dayId = intent.getStringExtra("dayId")
        val tvResultDay = findViewById<TextView>(R.id.tv_result_day)
        tvResultDay.text = "Jadwal Pelajaran hari $day"

        setupRecyclerView()

        if (dayId != null) {
            loadCoursesFromDatabase(dayId)
        } else {
            Log.e("DetailResultSchedule", "dayId is null")
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_detail_schedule)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        adapter = DetailResultScheduleAdapter(arrayListOf())
        recyclerView.adapter = adapter
    }

    private fun loadCoursesFromDatabase(dayId: String) {
        firestore.collection("days").document(dayId).collection("courses").get()
            .addOnSuccessListener { result ->
                val listDetailSchedule = result.map { document ->
                    document.toObject(MyDetailSchedule::class.java)
                }
                adapter.updateData(listDetailSchedule)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching courses", e)
            }
    }
}
