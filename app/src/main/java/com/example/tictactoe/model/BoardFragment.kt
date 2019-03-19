package com.example.tictactoe.model
import android.R
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import androidx.core.view.get
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
        popScaleAnimation(imageButton)
    }

    private fun popScaleAnimation(view: View){
        ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 1f),
            PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
        ).apply {
            interpolator = OvershootInterpolator()
        }.start()
    }

    private fun selectRouteClick(view: View) {
        updateClickable(false)

        val imageButton = view as ImageButton
        val position = imageButton.tag as String
        if (ticTakToeGame.makePlayerMoveIfLegal(position.toInt())) {
            setImage(imageButton, false)
            updateTurn()
            Handler().postDelayed({ // Feels like the AI is "thinking"
                aiTurn(ticTakToeGame.makeAIMove())
                updateClickable(true)
            }, (1..300).shuffled().first().toLong())
        }
    }

    private fun updateClickable(clickableStatus: Boolean) {
        (0 until grid_for_game.childCount).forEach { position ->
            grid_for_game[position].isClickable = clickableStatus
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
