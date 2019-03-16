package com.example.tictactoe.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tictactoe.R

class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setUpGame()
    }

    private fun setUpGame(){
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.info_content_frame, DisplayTurnFragment())
        ft.replace(R.id.game_content_frame, BoardFragment())
        ft.commit()
    }

    fun setText(name: String){
        val display = fragmentManager.findFragmentById(R.id.info_content_frame) as DisplayTurnFragment
        display.displayName(name)
    }
}
