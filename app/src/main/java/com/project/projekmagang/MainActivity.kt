package com.project.projekmagang

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.projekmagang.adapter.VPAdapter
import com.project.projekmagang.databinding.ActivityMainBinding
import com.project.projekmagang.fragment.FragmentExtra
import com.project.projekmagang.fragment.FragmentSchedule

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager2 = binding.viewPager2
        tabLayout = binding.tabLayout
        tabLayout.requestFocus()
        tabLayout.nextFocusDownId=R.id.create_button
        tabLayout.onFocusChangeListener= View.OnFocusChangeListener { view, hasFocus ->
        if(hasFocus){
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            view.scaleX = 1.2f
            view.scaleY = 1.2f
            view.elevation = 8f
        }else {
            view.setBackgroundResource(R.color.black)
            view.scaleX = 1.0f
            view.scaleY = 1.0f
            view.elevation = 0f
        }


        }

        setupViewPager()
    }

    private fun setupViewPager() {
        val vpAdapter = VPAdapter(this)
        vpAdapter.addFragment(FragmentSchedule())
        vpAdapter.addFragment(FragmentExtra())

        viewPager2.adapter = vpAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Jadwal Pelajaran"
                1 -> tab.text = "Ekstrakurikuler"
            }
        }.attach()
    }

}
