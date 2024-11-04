package com.project.projekmagang

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.project.projekmagang.ui.theme.Home


class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        val layoutGoToHome: Button = view.findViewById(R.id.next_button)

        layoutGoToHome.setOnClickListener {
            val intent = Intent(activity, Home::class.java)
            startActivity(intent)
        }

        return view
    }
}
