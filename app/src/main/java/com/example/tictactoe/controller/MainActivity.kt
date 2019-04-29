package com.example.tictactoe.controller

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.*
import com.example.tictactoe.R
import com.example.tictactoe.model.*
import com.example.tictactoe.model.database.Player
import kotlinx.android.synthetic.main.activity_game.*

class MainActivity : AppCompatActivity(), HighScoreFragment.OnListFragmentInteractionListener {
    private val fragmentManager = supportFragmentManager
    private lateinit var game: TicTacToe
    private var playerOne: String? = null
    private var playerTwo: String? = null
    private val backStackRootTag = "root_fragment"
    private lateinit var musicPlayer: MediaPlayer
    private var currentPosition = 0

    /************************************************ Life Cycle ******************************************************/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        fragmentManager.popBackStack(backStackRootTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        setUpMainMenu()

        musicPlayer = MediaPlayer.create(this, R.raw.hitchhikers_guide_to_the_galaxy)
        handleMusicPlayer()

        musicPlayer.setOnCompletionListener {
            musicPlayer.start()
            musicPlayer.isLooping = true
        }

        musicIcon.setOnClickListener {
            handleMusicPlayer()
        }

        with (background_root.background as AnimationDrawable) {//Create animation entrance
            setEnterFadeDuration(10)
            setExitFadeDuration(5000)
            start()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        handleMusicPlayer()
    }

    override fun onStart() {
        super.onStart()
        if (currentPosition > 0 && !musicPlayer.isPlaying) {
            handleMusicPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (musicPlayer.isPlaying) {
            handleMusicPlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }

    /************************************************ Game handling  **************************************************/
    fun getPlayerOne(): String = playerOne ?: "Not Selected"

    fun createGame(gameMode: GameMode) {
        game = when(gameMode == GameMode.PVP) {
            true -> TicTacToe(playerOne!!, playerTwo!!)
            false -> TicTacToe(playerOne!!, gameMode)
        }
        setUpGame(game)
    }

    fun goToPVPGame(playerTwoName: String) {
        playerTwo = playerTwoName
        game = TicTacToe(playerOne!!, playerTwo!!)
        setUpGame(game)
    }

    /********************************************** Transition Fragments **********************************************/
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

    fun setUpAIGame() {
        val choosePlayer = ChoosePlayerFragment()
        choosePlayer.setInstance(3)
        addToFragmentManagerWithBackStack(choosePlayer)
    }

    fun setUpAI(playerName: String) {
        playerOne = playerName
        addToFragmentManagerWithBackStack( SetUpGameAgainstAIFragment())
    }

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
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            .replace(R.id.game_content_frame, gameOverScreen)
            .commit()
    }

    fun playAgain(){
        game.resetGame()
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            .replace(R.id.game_content_frame, BoardFragment(game))
            .commit()
    }

    private fun addToFragmentManagerWithBackStack(fragment: Fragment) {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            .replace(R.id.game_content_frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun setUpMainMenu() {
        fragmentManager
            .beginTransaction()
            .setTransition(TRANSIT_FRAGMENT_FADE)
            .replace(R.id.game_content_frame, MainMenuFragment())
            .commit()
    }

    private fun setUpGame(game: TicTacToe) {
        addToFragmentManagerWithBackStack(BoardFragment(game))
    }

    /********************************************** Music Player ******************************************************/
    private fun handleMusicPlayer() {
        if (musicPlayer.isPlaying) {
            musicPlayer.pause()
            currentPosition = musicPlayer.currentPosition
            findViewById<ImageButton>(R.id.musicIcon).setImageResource(R.drawable.play)
        } else {
            currentPosition = musicPlayer.currentPosition
            musicPlayer.start()
            findViewById<ImageButton>(R.id.musicIcon).setImageResource( R.drawable.pause)
        }
    }

    /**************************************** Required by recycler view ***********************************************/
    override fun onListFragmentInteraction(item: Player?){ } //Do nothing, just need to implement
}
