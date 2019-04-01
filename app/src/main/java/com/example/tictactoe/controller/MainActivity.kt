package com.example.tictactoe.controller

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.*
import com.example.tictactoe.R
import com.example.tictactoe.model.*
import kotlinx.android.synthetic.main.activity_game.*

@Suppress("UNNECESSARY_SAFE_CALL")
class MainActivity : AppCompatActivity(), HighScoreFragment.OnListFragmentInteractionListener {
    private val fragmentManager = supportFragmentManager
    private lateinit var game:TicTakToe
    private var playerOne: String? = null
    private var playerTwo: String? = null
    private val BACK_STACK_ROOT_TAG = "root_fragment"
    private var musicPlayer: MediaPlayer? = null
    private var currentPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        setUpMainMenu()
        musicPlayer = MediaPlayer.create(this, R.raw.hitchhikers_guide_to_the_galaxy)
        letsListen()


        val animDrawable = background_root.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        letsListen()
    }

    override fun onPause() {
        super.onPause()
        if (musicPlayer != null){
            if (musicPlayer!!.isPlaying){
                currentPosition = musicPlayer!!.currentPosition
            }
        }
    }

    override fun onStop() {
        super.onStop()
        musicPlayer?.release()
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

    fun setUpHighScore() {
        addToFragmentManagerWithBackStack(HighScoreFragment())
    }

    fun gameIsOver() {
        val gameOverScreen = GameOverFragment()
        gameOverScreen
            .updatePlayerDatabase(
                game.getPlayerOne(),
                game.getPlayerTwo(),
                game.whoWon()
            )
        //fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            //.setCustomAnimations(R.animator.slide_out_right, R.animator.slide_in_left)
            .replace(R.id.game_content_frame, gameOverScreen)
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

    override fun onListFragmentInteraction(item: Player?){ println("Something happens") } //Do nothing, just need to implement

    private fun letsListen() = if (musicPlayer!!.isPlaying) {
        musicPlayer!!.pause()
        //findViewById<ImageButton>(R.id.musicIcon).setImageResource(R.drawable.play)
    } else {
        musicPlayer!!.start()
        //findViewById<ImageButton>(R.id.musicIcon).setImageResource( R.drawable.pause)
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
