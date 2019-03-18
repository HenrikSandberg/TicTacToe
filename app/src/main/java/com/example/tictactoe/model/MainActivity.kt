package com.example.tictactoe.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        setUpGameAIMode()
    }

    fun createGame(gameMode: GameMode){
        //TODO: Send game mode to fragment
        //TODO: Together with player info create a TicTakToe object and send to games
        println("$gameMode")
        setUpGame(TicTakToe(Player("Henrik"), gameMode))
    }

    private fun setUpGame(game: TicTakToe){
        fragmentManager
            .beginTransaction()
            .replace(R.id.game_content_frame, BoardFragment(game))
            .addToBackStack(null)
            .commit()
    }

    private fun setUpGameAIMode(){
        fragmentManager
            .beginTransaction()
            .replace(R.id.game_content_frame, SetUpGameAgainstAIFragment())
            .addToBackStack(null)
            .commit()
    }
}
