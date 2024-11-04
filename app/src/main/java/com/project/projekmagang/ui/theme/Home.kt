package com.project.projekmagang.ui.theme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.projekmagang.DetailScheduleActivity
import com.project.projekmagang.R
import com.project.projekmagang.adapter.ScheduleDayAdapter
import com.project.projekmagang.model.MyScheduleDay

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class Home : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ScheduleDayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        firestore = FirebaseFirestore.getInstance()
        setupRecyclerView()
        loadDaysFromDatabase()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_schedule)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        adapter = ScheduleDayAdapter(arrayListOf()) { scheduleDay, dayId ->
            val intent = Intent(this, DetailScheduleActivity::class.java)
            intent.putExtra("day", scheduleDay.day)
            intent.putExtra("dayId", dayId) // Mengirimkan ID dokumen day
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun loadDaysFromDatabase() {
        firestore.collection("days").get()
            .addOnSuccessListener { result ->
                val listScheduleDay = result.map { document ->
                    val scheduleDay = document.toObject(MyScheduleDay::class.java)
                    scheduleDay.id = document.id // Menyimpan ID dokumen
                    scheduleDay
                }
                adapter.updateData(listScheduleDay)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching days", e)
            }
    }
}
