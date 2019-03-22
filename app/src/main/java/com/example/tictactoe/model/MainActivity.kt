package com.example.tictactoe.model

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransaction.*
import com.example.tictactoe.R
import com.example.tictactoe.controller.GameMode
import com.example.tictactoe.controller.Player
import com.example.tictactoe.controller.TicTakToe

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private var game:TicTakToe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setUpMainMenu()
    }

    fun createGame(gameMode: GameMode){
        //TODO: Send game mode to fragment
        //TODO: Together with player info create a TicTakToe object and send to games
        setUpGame(TicTakToe("Henrik", gameMode))
    }

    fun setUpPVPGame() {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, ChoosePlayerFragment())
            .addToBackStack(null)
            .commit()
    }

    fun setUpAIGame(){
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, SetUpGameAgainstAIFragment())
            .addToBackStack(null)
            .commit()
    }

    fun setUpHighScore(){
        println("Set Up high score!")
    }

    private fun setUpMainMenu() {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, MainMenuFragment())
            .commit()
    }

    private fun setUpGame(game: TicTakToe) {
        fragmentManager
            .beginTransaction()
            //.setTransition(TRANSIT_FRAGMENT_FADE)
            .setTransition(TRANSIT_FRAGMENT_OPEN)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, BoardFragment(game))
            .addToBackStack(null)
            .commit()
    }
}
