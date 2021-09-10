package com.example.bloodbankmanagementsystem.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.bloodbankmanagementsystem.DonatebloodActivity
import com.example.bloodbankmanagementsystem.R

class DashboardActivity : Fragment() {
    private lateinit var donateblood: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_activity, container, false)
        donateblood = view.findViewById(R.id.donateblood)

        donateblood.setOnClickListener {
            startActivity(Intent(activity, DonatebloodActivity::class.java))
        }

        return view
    }

}