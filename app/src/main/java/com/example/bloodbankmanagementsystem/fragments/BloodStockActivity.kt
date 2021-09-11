package com.example.bloodbankmanagementsystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bloodbankmanagementsystem.R
import com.example.bloodbankmanagementsystem.adapter.DonorAdapter
import com.example.bloodbankmanagementsystem.repository.DonorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BloodStockActivity : Fragment() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blood_stock_activity, container, false)

        recyclerview = view.findViewById(R.id.recyclerview)
        swipeRefresh = view.findViewById(R.id.swipeRefresh)

        refreshApp()

        loadDonor()

        return view
    }

    private fun refreshApp() {
        swipeRefresh.setOnRefreshListener {
            loadDonor()

            swipeRefresh.isRefreshing = false
            Toast.makeText(activity, "refreshed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadDonor() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val donorRepository = DonorRepository()
                val response = donorRepository.getAllDonor()
                if (response.data != null) {

                    val lstDonors = response.data
                    withContext(Dispatchers.Main) {
                        recyclerview.layoutManager = LinearLayoutManager(activity)
                        recyclerview.adapter = DonorAdapter(requireContext(), lstDonors)



                    }

                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        activity,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}