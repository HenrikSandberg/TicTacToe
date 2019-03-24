package com.example.tictactoe.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoe.controller.SimpleRVAdapter
import com.example.tictactoe.R
class HighScoreFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private var strings = arrayOf("1", "2", "3", "4", "5", "6", "7")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_high_score, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = RecyclerView(this.context!!)
        recyclerView.layoutManager =  LinearLayoutManager(context)
        recyclerView.adapter = SimpleRVAdapter(strings)
    }
}
