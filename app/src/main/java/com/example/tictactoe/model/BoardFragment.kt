package com.example.tictactoe.model
import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.get
import com.example.tictactoe.controller.GameMode
import com.example.tictactoe.controller.Player
import com.example.tictactoe.controller.TicTakToe
import kotlinx.android.synthetic.main.fragment_board.*

@SuppressLint("ValidFragment")
class BoardFragment(game: TicTakToe) : Fragment() {
    private var ticTakToeGame = game
    //TicTakToe(Player("Henrik"), GameMode.IMPOSSIBLE)

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.tictactoe.R.layout.fragment_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateTurn()

        resetButton.setOnClickListener {
            ( 0 until grid_for_game.childCount).forEach{ position->
                setImage(grid_for_game[position] as ImageButton, true)
            }
            ticTakToeGame.resetGame()
            updateTurn()
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
                    if (ticTakToeGame.oJustPlayed()) "ic_circle" else "ic_cross",
                    "drawable",
                    activity!!.packageName
                )
            }
        )
    }

    private fun selectRouteClick(view: View) {
        val imageButton = view as ImageButton
        val position = imageButton.tag as String
        if (ticTakToeGame.makePlayerMoveIfLegal(position.toInt())) {
            setImage(imageButton, false)
            updateTurn()
            aiTurn(ticTakToeGame.makeAIMove())
        }
    }

    private fun aiTurn(move: Int) {
        if (move != -1) {
            ( 0 until grid_for_game.childCount).forEach { position ->
                if (grid_for_game[position].tag.toString().toInt() == move)
                    setImage(grid_for_game[position] as ImageButton, false)
            }
            updateTurn()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTurn(){
        val display = next_turn_text

        display.text =  when {
            ticTakToeGame.doWeHaveAWinner() -> "${ticTakToeGame.priviesPlayer()} won!"
            ticTakToeGame.noEmpty() -> "It's a draw"
            else -> ticTakToeGame.nextPlayer()
        }
    }
}
