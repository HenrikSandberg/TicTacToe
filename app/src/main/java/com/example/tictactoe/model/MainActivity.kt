package com.example.tictactoe.model


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.*
import com.example.tictactoe.R
import com.example.tictactoe.controller.GameMode
import com.example.tictactoe.controller.TicTakToe
import kotlinx.android.synthetic.main.fragment_choose_player.*

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

        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, choosePlayerOne)
            .addToBackStack(null)
            .commit()
    }

    fun setUpPlayerTwo(PlayerOneName: String){
        playerOne = PlayerOneName
        val choosePlayerTwo = ChoosePlayerFragment()
        choosePlayerTwo.setInstance(2)

        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, choosePlayerTwo)
            .addToBackStack(null)
            .commit()
    }

    fun goToPVPGame(playerTwoName: String){
        playerTwo = playerTwoName
        game = TicTakToe(playerOne!!, playerTwo!!)
        setUpGame(game)
    }

    fun setUpAIGame(){
        val choosePlayer = ChoosePlayerFragment()
        choosePlayer.setInstance(3)

        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, choosePlayer)
            .addToBackStack(null)
            .commit()
    }

    fun setUpAI(playerName: String){
        playerOne = playerName
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, SetUpGameAgainstAIFragment())
            .addToBackStack(null)
            .commit()
    }

    fun getPlayerOne(): String = playerOne ?: "Not Selected"

    //TODO: Create Fragment and populate with data
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
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setTransition(TRANSIT_FRAGMENT_OPEN)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, BoardFragment(game))
            .addToBackStack(null)
            .commit()
    }

    //TODO: Create winn fragemnt this fragment should give you two opperuneties, go back to main menu, or play again
    //TODO: Update database when game is over
}
