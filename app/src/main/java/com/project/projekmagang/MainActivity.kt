package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
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
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.projekmagang.adapter.VPAdapter
import com.project.projekmagang.databinding.ActivityMainBinding
import com.project.projekmagang.ui.theme.Home
import com.project.projekmagang.ui.theme.ProjekmagangTheme

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = binding.viewPager2
        tabLayout = binding.tabLayout

        setupViewPager()
    }

    private fun setupViewPager() {
        val vpAdapter = VPAdapter(this)
        vpAdapter.addFragment(Fragment1())
        vpAdapter.addFragment(Fragment2())
        vpAdapter.addFragment(Fragment3())

        viewPager2.adapter = vpAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Jadwal Pelajaran"
                1 -> tab.text = "Jadwal Piket"
                2 -> tab.text = "Ekstrakurikuler"
            }
        }.attach()
    }

}
