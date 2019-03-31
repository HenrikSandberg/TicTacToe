package com.example.tictactoe.controller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

import com.example.tictactoe.R
import kotlinx.android.synthetic.main.fragment_main_menue.*
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        game_button_manager.children.forEach{ button ->
            button.setOnClickListener{

                when(button.id){
                    R.id.pvp_button -> (activity as MainActivity).setUpPVPGame()
                    R.id.pvai_button -> (activity as MainActivity).setUpAIGame()
                    else -> (activity as MainActivity).setUpHighScore()
                }
            }
        }
    }
}
