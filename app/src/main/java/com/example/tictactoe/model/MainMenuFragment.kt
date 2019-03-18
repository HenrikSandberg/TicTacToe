package com.example.tictactoe.model

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.transition.Explode
import android.transition.Transition
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.tictactoe.R
class MainMenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menue, container, false)
    }
}
