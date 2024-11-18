package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.projekmagang.adapter.ResultDayScheduleAdapter
import com.project.projekmagang.adapter.ScheduleDayAdapter
import com.project.projekmagang.model.MyScheduleDay

class ResultDaySchedule : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ResultDayScheduleAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_day_schedule)

        firestore = FirebaseFirestore.getInstance()
        recyclerView = findViewById(R.id.rv_schedule)

        setupRecyclerView()
        loadDaysFromDatabase()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = ResultDayScheduleAdapter(arrayListOf()) { scheduleDay, dayId ->
            val intent = Intent(this, DetailResultSchedule::class.java)
            intent.putExtra("day", scheduleDay.day)
            intent.putExtra("dayId", dayId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun loadDaysFromDatabase() {
        firestore.collection("days").get()
            .addOnSuccessListener { result ->
                val listScheduleDay = result.map { document ->
                    val scheduleDay = document.toObject(MyScheduleDay::class.java)
                    scheduleDay.id = document.id
                    scheduleDay
                }
                adapter.updateData(listScheduleDay)

                if (listScheduleDay.isNotEmpty()) {
                    val firstItemPosition = 0
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    layoutManager.scrollToPosition(firstItemPosition)

                    recyclerView.post {
                        val firstItemView = recyclerView.findViewHolderForAdapterPosition(firstItemPosition)?.itemView
                        firstItemView?.requestFocus()
                    }
                }
            }

    }
}

