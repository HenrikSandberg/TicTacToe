package com.example.tictactoe.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.*
import com.example.tictactoe.R
import com.example.tictactoe.controller.GameMode
import com.example.tictactoe.controller.Player
import com.example.tictactoe.controller.TicTakToe




class MainActivity : AppCompatActivity(), HighScoreFragment.OnListFragmentInteractionListener {
    private val fragmentManager = supportFragmentManager
    private lateinit var game:TicTakToe
    private var playerOne: String? = null
    private var playerTwo: String? = null
    private val BACK_STACK_ROOT_TAG = "root_fragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        setUpMainMenu()
    }

    fun createGame(gameMode: GameMode) {
        game = when(gameMode == GameMode.PVP) {
            true -> TicTakToe(playerOne!!, playerTwo!!)
            false -> TicTakToe(playerOne!!, gameMode)
        }
        setUpGame(game)
    }

    fun setUpPVPGame() {
        val choosePlayerOne = ChoosePlayerFragment()
        choosePlayerOne.setInstance(1)
        addToFragmentManagerWithBackStack(choosePlayerOne)
    }

    fun setUpPlayerTwo(PlayerOneName: String) {
        playerOne = PlayerOneName
        val choosePlayerTwo = ChoosePlayerFragment()
        choosePlayerTwo.setInstance(2)
        addToFragmentManagerWithBackStack(choosePlayerTwo)
    }

    fun goToPVPGame(playerTwoName: String) {
        playerTwo = playerTwoName
        game = TicTakToe(playerOne!!, playerTwo!!)
        setUpGame(game)
    }

    fun setUpAIGame() {
        val choosePlayer = ChoosePlayerFragment()
        choosePlayer.setInstance(3)
        addToFragmentManagerWithBackStack(choosePlayer)
    }

    fun setUpAI(playerName: String) {
        playerOne = playerName
        addToFragmentManagerWithBackStack( SetUpGameAgainstAIFragment())
    }

    fun getPlayerOne(): String = playerOne ?: "Not Selected"

    fun setUpHighScore(){
        addToFragmentManagerWithBackStack(HighScoreFragment())
    }

    fun gameIsOver(){
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, GameOverFragment())
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .commit()
    }

    fun playAgain(){
        game.resetGame()
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, BoardFragment(game))
            .addToBackStack(BACK_STACK_ROOT_TAG)
            .commit()
    }

    override fun onListFragmentInteraction(item: Player?){
        println("Something happens")
    }

    private fun addToFragmentManagerWithBackStack(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun setUpMainMenu() {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, MainMenuFragment())
            .commit()
    }

    private fun setUpGame(game: TicTakToe) {  addToFragmentManagerWithBackStack(BoardFragment(game)) }
}
