package com.example.tictactoe.controller
import android.R
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.forEach
import com.example.tictactoe.model.GameMode
import kotlinx.android.synthetic.main.fragment_board.*
import java.util.*
import java.util.concurrent.TimeUnit


@SuppressLint("ValidFragment")
class BoardFragment(game: TicTakToe) : Fragment() {
    private var ticTakToeGame = game

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.example.tictactoe.R.layout.fragment_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateTurn()
        grid_for_game.forEach { item -> item.setOnClickListener { selectRouteClick(item) } }
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onPause() {
        super.onPause()
        timer.stop()
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
        updateTurn()
    }

    private fun popScaleAnimation(view: ImageButton){
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
        val imageButton = view as ImageButton

        if (ticTakToeGame.makePlayerMoveIfLegal((imageButton.tag as String).toInt())) {
            setImage(imageButton, false)

            if(ticTakToeGame.getGameMode() != GameMode.PVP) {
                updateClickable(false)

                val move = ticTakToeGame.makeAIMove()
                if (move != -1) {
                    Handler().postDelayed({ // Feels like the AI is "thinking"
                        aiTurn(move)
                        updateClickable(true)
                    }, (50..300).shuffled().first().toLong())
                } else {
                    updateClickable(true)
                }
            }
        }
    }

    private fun updateClickable(clickableStatus: Boolean) {
        grid_for_game.forEach { item -> item.isEnabled = clickableStatus }
    }

    private fun aiTurn(move: Int) {
        grid_for_game.forEach { item ->
            if (item.tag.toString().toInt() == move)
                setImage(item as ImageButton, false)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateTurn() {
        (next_turn_text as TextView).text =  when {
            ticTakToeGame.doWeHaveAWinner() -> "${ticTakToeGame.previousPlayer()} won!"
            ticTakToeGame.noEmpty() -> "It's a draw"
            else -> ticTakToeGame.nextPlayer()
        }
        if (ticTakToeGame.doWeHaveAWinner()) {
            (activity as MainActivity).gameIsOver()
            
        } else if (ticTakToeGame.noEmpty()) {
            (activity as MainActivity).gameIsOver()
        }
    }
}
