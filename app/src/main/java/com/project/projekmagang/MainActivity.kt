package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.tv.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import com.project.projekmagang.databinding.ActivityMainBinding
import com.project.projekmagang.ui.theme.Home
import com.project.projekmagang.ui.theme.ProjekmagangTheme

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }
    private fun navigateToSchedule(){
        val intent = Intent(this, Home::class.java).apply {
        }
        startActivity(intent)
    }
    private fun setOnClickListener(){
        binding.cardSchedule.setOnClickListener{
            navigateToSchedule()
        }
    }
}

