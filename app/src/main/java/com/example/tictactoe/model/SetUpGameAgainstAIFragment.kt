package com.example.tictactoe.model

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.DialogFragment
import com.example.tictactoe.R
import com.example.tictactoe.controller.GameMode
import kotlinx.android.synthetic.main.fragment_set_up_game_against_ai.*

class SetUpGameAgainstAIFragment : DialogFragment() {

    private var gameMode = GameMode.IMPOSSIBLE

    private val computerTexts = arrayOf(
        "This is the on board computer to the Heart of Gold." +
                " The universes fastest spaceship. However, Eddie the Computer is not that impressive.." +
                " He only wants to please you and trust me that gets quite annoying fast...",

        "Originally built by the Sirius Cybernetics Corporation's GPP (Genuine People Personalities) technology," +
                " Marvin is afflicted with severe depression and boredom, in part because he has a brain the size of a " +
                "planet which he is seldom, if ever, given the chance to use. Beating him is tough, but it is possible."

        ,"Deep Thought is a supernatural-computer programmed to calculate the answer the Ultimate Question of Life, the Universe, and Everything." +
            " Winning against Deep Thought is impossible. However, someone do say that there is one way.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_set_up_game_against_ai, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        game_mode_selector.children.forEach {
            val radioButton = it as RadioButton
            radioButton.setOnClickListener{
                if (radioButton.isChecked){
                    gameMode = when(radioButton.id) {
                        R.id.easy -> GameMode.EASY
                        R.id.medium -> GameMode.HARD
                        else -> GameMode.IMPOSSIBLE
                    }
                    updateUI()
                }
            }
        }

        set_up_game_against_ai_button.setOnClickListener {
            (activity as MainActivity).createGame(gameMode)
        }
        updateUI()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(){
        when(gameMode){
            GameMode.EASY -> {
                ai_description.text = computerTexts[0]
                title_text.text = "Eddie the Computer"
                points.text = "winning: +1pts draw: -1 loosing: -10"
            }
            GameMode.HARD -> {
                ai_description.text = computerTexts[1]
                title_text.text = "Marvin"
                points.text = "winning: +5 draw: 0 loosing: -5"
            }
            else -> {
                ai_description.text = computerTexts[2]
                title_text.text = "Deep Thought"
                points.text = "winning: +1000 draw: 0 loosing: -1"
            }
        }
    }
}
