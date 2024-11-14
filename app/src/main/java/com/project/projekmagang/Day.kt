package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.project.projekmagang.adapter.ScheduleDayAdapter
import com.project.projekmagang.model.MyScheduleDay

import com.google.firebase.firestore.FirebaseFirestore

class Day : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ScheduleDayAdapter
    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var errorTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutState: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_day)

        firestore = FirebaseFirestore.getInstance()

        progressBar = findViewById(R.id.pb_loading)
        errorTextView = findViewById(R.id.tv_error)
        recyclerView = findViewById(R.id.rv_schedule)
        layoutState = findViewById(R.id.layout_state)

        setupRecyclerView()
        loadDaysFromDatabase()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        adapter = ScheduleDayAdapter(arrayListOf()) { scheduleDay, dayId ->
            val intent = Intent(this, DetailScheduleActivity::class.java)
            intent.putExtra("day", scheduleDay.day)
            intent.putExtra("dayId", dayId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun loadDaysFromDatabase() {
        layoutState.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE

        firestore.collection("days").get()
            .addOnSuccessListener { result ->
                val listScheduleDay = result.map { document ->
                    val scheduleDay = document.toObject(MyScheduleDay::class.java)
                    scheduleDay.id = document.id
                    scheduleDay
                }
                adapter.updateData(listScheduleDay)

                layoutState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                if (listScheduleDay.isNotEmpty()) {
                    val firstItemPosition = 0
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    layoutManager.scrollToPosition(firstItemPosition)

                    recyclerView.post {
                        val firstItemView = recyclerView.findViewHolderForAdapterPosition(firstItemPosition)?.itemView
                        firstItemView?.requestFocus()
                    }
                }

                if (listScheduleDay.isEmpty()) {
                    errorTextView.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener { e ->
                layoutState.visibility = View.GONE
                recyclerView.visibility = View.GONE
                errorTextView.visibility = View.VISIBLE
                errorTextView.text = "Error fetching data: ${e.message}"
                Log.e("Firestore", "Error fetching days", e)
            }
    }
}
