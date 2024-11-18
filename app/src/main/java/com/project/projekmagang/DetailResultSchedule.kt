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

        val day = intent.getStringExtra("day") ?: "Tidak diketahui"
        val dayId = intent.getStringExtra("dayId")

        val tvResultDay = findViewById<TextView>(R.id.tv_result_day)
        tvResultDay.text = "Jadwal Pelajaran hari $day"

        setupRecyclerView()

        if (!dayId.isNullOrEmpty()) {
            loadCoursesFromDatabase(dayId)
        } else {
            Log.e("DetailResultSchedule", "dayId is null or empty")
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_detail_schedule)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.isFocusable = true
        recyclerView.isFocusableInTouchMode = true

        adapter = DetailResultScheduleAdapter(arrayListOf())
        recyclerView.adapter = adapter

        recyclerView.post {
            recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
        }
    }

    private fun loadCoursesFromDatabase(dayId: String) {
        firestore.collection("days").document(dayId).collection("courses").get()
            .addOnSuccessListener { result ->
                val listDetailSchedule = result.mapNotNull { document ->
                    document.toObject(MyDetailSchedule::class.java)
                }
                adapter.updateData(ArrayList(listDetailSchedule))

                // Fokuskan item pertama setelah data di-update
                findViewById<RecyclerView>(R.id.rv_detail_schedule).post {
                    findViewById<RecyclerView>(R.id.rv_detail_schedule)
                        .findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching courses", e)
            }
    }
}

