package com.example.bloodbankmanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbankmanagementsystem.R
import com.example.bloodbankmanagementsystem.adapter.EventAdapter
import com.example.bloodbankmanagementsystem.data.EventArticle

class EventActivity : Fragment() {
    lateinit var eventAdapter: EventAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_event_activity, container, false)
        recyclerView = view.findViewById(R.id.recyclerview)

        eventAdapter = EventAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
        val eventItems = mutableListOf<EventArticle>()
        for (i in 0..40) {
            eventItems.add(
                EventArticle(
                    "Blood Donation will be held in this September $i",
                    "Don't miss this chance",
                    "https://www.eventsinnepal.com/timthumb.php?src=./uploads/a087692d4be31e9e1469507ae9e98e35_11012915_10205286951747095_6535343468773218858_n.jpg&w=498&h=224",
                    "$i minutes ago"
                )
            )
        }

        eventAdapter.submitList(eventItems)

        return view
    }

}