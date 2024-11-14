package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.firebase.firestore.FirebaseFirestore
import com.project.projekmagang.R
import com.project.projekmagang.adapter.ListExtraAdapter
import com.project.projekmagang.model.Extra

class ExtraActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListExtraAdapter
    private val extraList = mutableListOf<Extra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra)

        recyclerView = findViewById(R.id.rv_extra)
        adapter = ListExtraAdapter(extraList) { extra ->
            val intent = Intent(this, DetailExtraActivity::class.java).apply {
                putExtra("extra_image", extra.image)
                putExtra("extra_detail", extra.detail)
            }
            startActivity(intent)
        }

        val layoutManager = GridLayoutManager(this, 4)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        fetchExtras()
    }

    private fun fetchExtras() {
        val db = FirebaseFirestore.getInstance()
        db.collection("extra")
            .get()
            .addOnSuccessListener { documents ->
                extraList.clear()
                for (document in documents) {
                    val extra = document.toObject(Extra::class.java)
                    extraList.add(extra)
                }
                adapter.notifyDataSetChanged()

                // Meminta fokus pada item pertama setelah data diperbarui
                recyclerView.post {
                    if (extraList.isNotEmpty()) {
                        recyclerView.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                    }
                }
            }
            .addOnFailureListener { e ->
                // Tangani kesalahan jika diperlukan
            }
    }
}