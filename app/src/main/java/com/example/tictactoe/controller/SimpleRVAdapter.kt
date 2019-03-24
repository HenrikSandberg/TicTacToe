package com.example.tictactoe.controller

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleRVAdapter(data: Array<String>) : RecyclerView.Adapter<SimpleViewHolder>() {
    private val dataSource: Array<String> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = TextView(parent.context)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.textView.text = dataSource[position]
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}

class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textView: TextView =  itemView as TextView
}

