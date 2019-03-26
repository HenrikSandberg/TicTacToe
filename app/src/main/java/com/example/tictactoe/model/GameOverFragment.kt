package com.example.tictactoe.model


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tictactoe.R
import kotlinx.android.synthetic.main.fragment_game_over.*

class GameOverFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rematch_button.setOnClickListener{ (activity as MainActivity).playAgain() }
        back_to_main_menu.setOnClickListener{ (activity as MainActivity).setUpMainMenu() }
    }

    fun setText(topText: String, winnerName: String){
        top_title.text = topText
        winner_name.text = winnerName
    }

    //TODO: Add code to update database with winner and winners new score. Also update looser!
}
