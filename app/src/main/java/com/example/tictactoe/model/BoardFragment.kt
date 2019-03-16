package com.example.tictactoe.model
import android.R
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
    private var ai = GameAI(GameAI.GameMode.IMPOSSIBLE)
    private val playAgainstAI = true

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.tictactoe.R.layout.fragment_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        resetButton.setOnClickListener {
            ( 0 until grid_for_game.childCount).forEach{ position->
                setImage(grid_for_game[position] as ImageButton, true)
            }
            ticTakToeGame.resetGame()
            updateTurn("Player 1")
        }

        (0 until grid_for_game.childCount).forEach { position ->
            grid_for_game[position].setOnClickListener { imageButton ->
                selectRouteClick(imageButton)
            }
        }
    }

    private fun setImage(imageButton: ImageButton, reset: Boolean) {
        imageButton.setImageResource(
            when (reset) {
                true -> R.color.transparent
                false -> resources.getIdentifier (
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
            updateTurn(if (ticTakToeGame.isItFirstTurn()) "Player 1" else "Player 2")

            if (playAgainstAI && !ticTakToeGame.noEmpty() && !ticTakToeGame.haveAWinner()){
                aiMove()
                updateTurn(if (ticTakToeGame.isItFirstTurn()) "Player 1" else "Player 2")
            }

            if (ticTakToeGame.haveAWinner())
                updateTurn("${if (!ticTakToeGame.isItFirstTurn()) "Player 1" else "Player 2"} has won!")

            if (ticTakToeGame.noEmpty())
                updateTurn("It's a draw")
        }
    }

    private fun aiMove(){
        val move = ai.makeMove(ticTakToeGame.lookAtBoard().clone())
        ticTakToeGame.makeMove(move)
        ( 0 until grid_for_game.childCount).forEach{ position ->
            if (grid_for_game[position].tag.toString().toInt() == move)
                setImage(grid_for_game[position] as ImageButton, false)
        }
    }

    private fun updateTurn(name: String){
        val main = activity as MainActivity
        main.setText(name)
    }
}
