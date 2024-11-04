package com.project.projekmagang

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        // Mendapatkan ID hari dari intent
        scheduleday=intent.getStringExtra("day")
        dayId = intent.getStringExtra("dayId")
        val textView = findViewById<TextView>(R.id.tv_day_detail)
        textView.text = "$scheduleday"

        firestore = FirebaseFirestore.getInstance()

        // Mengatur RecyclerView dan adapter dengan listener untuk klik item
        adapter = DetailScheduleAdapter(listDetailSchedule) { selectedCourse ->
            showConfirmationDialog(selectedCourse)
        }
        val layoutManager = GridLayoutManager(this, 3)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_detail_schedule)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        // Panggil metode loadCoursesFromDatabase
        loadCoursesFromDatabase()
    }

    private fun loadCoursesFromDatabase() {
        firestore.collection("courses")
            .get()
            .addOnSuccessListener { result ->
                listDetailSchedule.clear()
                for (document in result) {
                    val course = document.toObject(MyDetailSchedule::class.java)
                    listDetailSchedule.add(course)
                }
                adapter.notifyDataSetChanged()
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
            // Mengecek apakah course sudah ada
            firestore.collection("days").document(id)
                .collection("courses")
                .whereEqualTo("course", course.course) // Ganti "course" sesuai dengan field yang ada di Firestore
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.isEmpty) {
                        // Course belum ada, simpan ke Firestore
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
                        // Course sudah ada, beri tahu pengguna
                        Toast.makeText(this, "Course '${course.course}' sudah ada.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error checking course existence", e)
                }
        }
    }

}

