package com.project.projekmagang

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.project.projekmagang.adapter.DetailScheduleAdapter
import com.project.projekmagang.model.MyDetailSchedule

class DetailScheduleActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: DetailScheduleAdapter
    private val listDetailSchedule = arrayListOf<MyDetailSchedule>()
    private var dayId: String? = null
    private var scheduleday: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_schedule)

        scheduleday = intent.getStringExtra("day")
        dayId = intent.getStringExtra("dayId")
        val textView = findViewById<TextView>(R.id.tv_day_detail)
        textView.text = "$scheduleday"

        firestore = FirebaseFirestore.getInstance()

        adapter = DetailScheduleAdapter(listDetailSchedule) { selectedCourse ->
            showConfirmationDialog(selectedCourse)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_detail_schedule)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        loadCoursesFromDatabase {
            // Once data is loaded, request focus for the first item
            recyclerView.post {
                recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
            }
        }
    }

    private fun loadCoursesFromDatabase(onDataLoaded: () -> Unit) {
        firestore.collection("courses")
            .get()
            .addOnSuccessListener { result ->
                listDetailSchedule.clear()
                for (document in result) {
                    val course = document.toObject(MyDetailSchedule::class.java)
                    listDetailSchedule.add(course)
                }
                adapter.notifyDataSetChanged()
                onDataLoaded()  // Callback to set focus after data load
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching courses", e)
            }
    }

    private fun showConfirmationDialog(course: MyDetailSchedule) {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah Anda ingin menyimpan course '${course.course}' ke dalam koleksi days untuk hari $scheduleday?")
            .setPositiveButton("Yes") { _, _ ->
                saveCourseToDay(course)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun saveCourseToDay(course: MyDetailSchedule) {
        dayId?.let { id ->
            firestore.collection("days").document(id)
                .collection("courses")
                .whereEqualTo("course", course.course)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        val courseData = hashMapOf(
                            "course" to course.course,
                            "timestamp" to System.currentTimeMillis()
                        )

                        firestore.collection("days").document(id)
                            .collection("courses")
                            .add(courseData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Course berhasil disimpan ke hari dengan ID $id", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Error saving course to day", e)
                                Toast.makeText(this, "Gagal menyimpan course", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Course '${course.course}' sudah ada.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error checking course existence", e)
                }
        }
    }
}
