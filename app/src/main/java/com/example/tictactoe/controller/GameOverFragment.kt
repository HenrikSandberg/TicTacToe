package com.example.tictactoe.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.R
import com.example.tictactoe.model.database.Player
import com.example.tictactoe.model.database.PlayerModel
import kotlinx.android.synthetic.main.fragment_game_over.*

class GameOverFragment : DialogFragment() {
    private lateinit var playerModel: PlayerModel
    private var p1: String? = null
    private var p2: String? = null
    private var whoWon: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_over, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rematch_button.setOnClickListener{ (activity as MainActivity).playAgain() }
        back_to_main_menu.setOnClickListener{ (activity as MainActivity).setUpMainMenu() }
        var whenObserved = false
        if (p1 != null && whoWon != null && p2 != null) {
            playerModel = ViewModelProviders.of(this).get(PlayerModel::class.java)
            playerModel.allPlayers.observe(this, Observer {
                if (!whenObserved && it != null) {
                    update(it)
                    whenObserved = !whenObserved
                }
            })
        }

        setText()
    }

    private fun update (players: List<Player>) {
        if (p1 != null && p2 != null && whoWon != null) {
            val player1 = getPlayer(p1!!, players)
            if (player1 != null) {
                updatePlayerData(player1, whoWon!!, p2!!)
            } else {
                insertNewPlayer(p1!!, whoWon!!, p2!!)
            }

            val player2 = getPlayer(p2!!, players)
            val inverseWhoWon = when(whoWon!!) {
                1 -> 2
                2 -> 1
                else -> 0
            }

            if (player2 != null) {
                updatePlayerData(player2, inverseWhoWon, p1!!)
            } else {
                insertNewPlayer(p2!!, inverseWhoWon, p1!!)
            }
        }
    }

    private fun updatePlayerData(player: Player, winnerState: Int, nameOfOtherPlayer: String){
        player.score += updateScore(winnerState, nameOfOtherPlayer)
        if (winnerState == 1) player.wins++
        playerModel.update(player)
    }

    private fun insertNewPlayer(playerName: String, winnerState: Int, nameOfOtherPlayer: String){
        playerModel.insert( //Create Player in database
            Player(
                playerName,
                if (winnerState == 1)  1 else 0,
                updateScore(winnerState, nameOfOtherPlayer)
            )
        )
    }

    private fun getPlayer(player: String, players: List<Player>): Player?{
        return (
            if (players.any { playerInList ->  playerInList.name == player })
                players.first { playerInList -> playerInList.name == player }
            else null
        )
    }

    private fun updateScore(win: Int, otherPlayer: String): Int{
        when (win) {
            1 -> return when (otherPlayer) {
                "Deep Thought" -> 1000
                "Marvin" -> 5
                "Eddie" -> 1
                else -> 3
            }
            2 -> return when (otherPlayer) {
                "Deep Thought" -> -1
                "Marvin" -> -5
                "Eddie" -> -1
                else -> 0
            }
            else -> return when (otherPlayer) {
                "Deep Thought" -> 0
                "Marvin" -> 0
                "Eddie" -> -1
                else -> 1
            }
        }
    }

    fun setText(){
        if (p1 != null && p2 != null && whoWon != null){
            when(whoWon){
                1 -> {
                    top_title.text = "Winner is"
                    winner_name.text = p1
                }
                2 -> {
                    top_title.text = "Winner is"
                    winner_name.text = p2
                }
                else -> {
                    top_title.text = "It's a draw"
                }
            }
        }
    }

    fun updatePlayerDatabase(playerOne: String, playerTwo: String, winnerOrDraw: Int){
        p1 = playerOne
        p2 = playerTwo
        whoWon = winnerOrDraw
    }
}
