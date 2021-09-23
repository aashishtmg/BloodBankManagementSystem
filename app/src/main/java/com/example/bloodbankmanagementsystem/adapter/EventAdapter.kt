package com.example.bloodbankmanagementsystem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodbankmanagementsystem.R
import com.example.bloodbankmanagementsystem.data.EventArticle

class EventAdapter :
    ListAdapter<EventArticle, EventAdapter.EventArticleViewHolder>(EventDiffCallback()) {

    class EventArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventArticleViewHolder(inflater.inflate(R.layout.custom_event_layout,parent,false))
    }

    override fun onBindViewHolder(holder: EventArticleViewHolder, position: Int) {

    }

}

class EventDiffCallback : DiffUtil.ItemCallback<EventArticle>() {
    override fun areItemsTheSame(oldItem: EventArticle, newItem: EventArticle): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: EventArticle, newItem: EventArticle): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }

}