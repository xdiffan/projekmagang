package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat


class FragmentSchedule : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        val layoutGoToHome: Button = view.findViewById(R.id.create_button)
        val checkButton: Button = view.findViewById(R.id.next_button)
        layoutGoToHome.nextFocusUpId = R.id.tabLayout
        layoutGoToHome.nextFocusLeftId = R.id.tabLayout


        layoutGoToHome.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.scaleX = 1.3f
                view.scaleY = 1.3f
                view.elevation = 8f
            } else {
                view.scaleX = 1.0f
                view.scaleY = 1.0f
                view.elevation = 0f
            }
        }
        checkButton.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.scaleX = 1.3f
                view.scaleY = 1.3f
                view.elevation = 8f
            } else {
                view.scaleX = 1.0f
                view.scaleY = 1.0f
                view.elevation = 0f
            }
        }

        layoutGoToHome.setOnClickListener {
            val intent = Intent(activity, Day::class.java)
            startActivity(intent)
        }

        checkButton.setOnClickListener {
            val intent = Intent(activity, ResultDaySchedule::class.java)
            startActivity(intent)
        }

        return view
    }
}
