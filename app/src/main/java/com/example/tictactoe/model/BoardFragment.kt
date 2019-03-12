package com.example.tictactoe.model

import android.inputmethodservice.Keyboard
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import androidx.core.view.get
import com.example.tictactoe.controller.TicTakToe
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.fragment_board.*



class BoardFragment : Fragment() {

    private var ticTakToeGame = TicTakToe()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.tictactoe.R.layout.fragment_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        for (i in 0 until grid_for_game.childCount)
            grid_for_game[i].setOnClickListener {
                selectRouteClick(it)
            }
    }

    private fun selectRouteClick(view: View){
        val imageButton = view as ImageButton
        val coordinates = imageButton.tag as String
        val firstTurn = ticTakToeGame.isItFirstTurn()

        if (ticTakToeGame.setBrick(coordinates[0].toString().toInt(), coordinates[1].toString().toInt())) {

            imageButton.setImageResource(
                resources.getIdentifier(
                    if (firstTurn) "o" else "x",
                    "drawable",
                    activity!!.packageName
                )
            )

            if (ticTakToeGame.haveAWinner())
                println("We have a winner!")
        }
    }
}
