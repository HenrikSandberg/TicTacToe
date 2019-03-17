package com.example.tictactoe.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.R
import com.example.tictactoe.controller.GameMode

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setUpGameAIMode()
    }

    fun createGame(gameMode: GameMode){
        //TODO: Send game mode to fragment
        //TODO: Together with palyer info create a TicTakToe object and send to game
        println("$gameMode")
        setUpGame()
    }

    private fun setUpGame(){
        //TODO: Add backstack and add menu to backstack
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.game_content_frame, BoardFragment())
        ft.commit()
    }

    private fun setUpGameAIMode(){
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.game_content_frame, SetUpGameAgainstAIFragment())
        ft.commit()
    }
}
