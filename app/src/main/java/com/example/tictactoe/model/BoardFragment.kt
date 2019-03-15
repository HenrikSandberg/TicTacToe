package com.example.tictactoe.model
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.get
import com.example.tictactoe.controller.GameAI
import com.example.tictactoe.controller.TicTakToe
import kotlinx.android.synthetic.main.fragment_board.*

class BoardFragment : Fragment() {

    private var ticTakToeGame = TicTakToe()
    private var ai = GameAI()
    private val playAgainstAI = true
    private val isHardModeOn = true

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.tictactoe.R.layout.fragment_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        resetButton.setOnClickListener {
            ticTakToeGame.resetGame()
            for (i in 0 until grid_for_game.childCount){
                setImage(grid_for_game[i] as ImageButton, true)
            }
        }

        for (i in 0 until grid_for_game.childCount) {
            grid_for_game[i].setOnClickListener { imageButton ->
                selectRouteClick(imageButton)
            }
        }
    }

    private fun setImage(imageButton: ImageButton, reset: Boolean) {
        imageButton.setImageResource(
            if (reset) {
                android.R.color.transparent
            } else {
                resources.getIdentifier(
                    if (!ticTakToeGame.isItFirstTurn()) "o" else "x", //Make move changes player turn so if current player is player two then it was player one that just did a move
                    "drawable",
                    activity!!.packageName
                )
            }
        )
    }

    private fun selectRouteClick(view: View){
        val imageButton = view as ImageButton
        val position = imageButton.tag as String

        if (ticTakToeGame.makeMove(position.toInt())) {
            setImage(imageButton, false)

            if (playAgainstAI && !ticTakToeGame.noEmpty() && !ticTakToeGame.haveAWinner())
                aiMove()

            if (ticTakToeGame.haveAWinner())
                println("We have a winner!")
        }
    }

    private fun aiMove(){
        val move = ai.makeMove(ticTakToeGame.lookAtBoard().clone(), isHardModeOn)
        ticTakToeGame.makeMove(move)
        ( 0 until grid_for_game.childCount).forEach{
            if (grid_for_game[it].tag.toString().toInt() == move){
                setImage(grid_for_game[it] as ImageButton, false)
            }
        }
    }
}
