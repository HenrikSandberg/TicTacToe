package com.example.tictactoe.model


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

    fun setUpPVPGame(){
        val choosePlayerOne = ChoosePlayerFragment()
        choosePlayerOne.setInstance(1)
        addToFragmentManagerWithBackStack(choosePlayerOne)
    }

    fun setUpPlayerTwo(PlayerOneName: String){
        playerOne = PlayerOneName
        val choosePlayerTwo = ChoosePlayerFragment()
        choosePlayerTwo.setInstance(2)
        addToFragmentManagerWithBackStack(choosePlayerTwo)
    }

    fun goToPVPGame(playerTwoName: String){
        playerTwo = playerTwoName
        game = TicTakToe(playerOne!!, playerTwo!!)
        setUpGame(game)
    }

    fun setUpAIGame(){
        val choosePlayer = ChoosePlayerFragment()
        choosePlayer.setInstance(3)
        addToFragmentManagerWithBackStack(choosePlayer)
    }

    fun setUpAI(playerName: String){
        playerOne = playerName
        addToFragmentManagerWithBackStack( SetUpGameAgainstAIFragment())
    }

    fun getPlayerOne(): String = playerOne ?: "Not Selected"

    fun setUpHighScore(){ addToFragmentManagerWithBackStack(HighScoreFragment()) }

    private fun addToFragmentManagerWithBackStack(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setUpMainMenu() {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, MainMenuFragment())
            .commit()
    }

    private fun setUpGame(game: TicTakToe) {  addToFragmentManagerWithBackStack(BoardFragment(game)) }
}
