package com.example.tictactoe.model


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction.*
import com.example.tictactoe.R
import com.example.tictactoe.controller.GameMode
import com.example.tictactoe.controller.TicTakToe

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private lateinit var game:TicTakToe
    private var playerOne: String? = null
    private var playerTwo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setUpMainMenu()
    }

    fun createGame(gameMode: GameMode){
        game = when (gameMode == GameMode.PVP){
            true -> TicTakToe(playerOne!!, playerTwo!!)
            else -> TicTakToe(playerOne!!, gameMode)
        }
        setUpGame(game)
    }

    fun setUpPVPGame() {
        setUpPlayerOne()
    }

    private fun setUpPlayerOne(){
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, ChoosePlayerFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setUpPlayerTwo(){
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
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, SetUpGameAgainstAIFragment())
            .addToBackStack(null)
            .commit()
    }

    fun setUpHighScore(){
        println("Set Up high score!")
    }

    fun setPlayerOne(name: String){ playerOne = name }
    fun setPlayerTwo(name: String){ playerTwo = name}

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
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setTransition(TRANSIT_FRAGMENT_OPEN)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, BoardFragment(game))
            .addToBackStack(null)
            .commit()
    }
}
