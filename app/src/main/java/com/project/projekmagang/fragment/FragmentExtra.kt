package com.project.projekmagang.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.project.projekmagang.ExtraActivity
import com.project.projekmagang.R


class FragmentExtra : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_extra, container, false)


        val goToExtraActivity: Button =view.findViewById(R.id.next_button)
        goToExtraActivity.nextFocusUpId= R.id.tabLayout
        goToExtraActivity.nextFocusLeftId= R.id.tabLayout

        goToExtraActivity.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
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
        goToExtraActivity.setOnClickListener {
            val intent = Intent(activity, ExtraActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}