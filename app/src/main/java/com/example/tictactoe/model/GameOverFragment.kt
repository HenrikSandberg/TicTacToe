package com.example.tictactoe.model

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.R
import com.example.tictactoe.controller.Player
import com.example.tictactoe.controller.PlayerModel
import kotlinx.android.synthetic.main.fragment_game_over.*

class GameOverFragment : Fragment() {
    private lateinit var playerModel: PlayerModel
    private var topText = ""
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
    }

    private fun update (players: List<Player>) {
        if (p1 != null && p2 != null && whoWon != null){
            val player1:Player? = getPlayer(p1!!, players)
            val player2:Player? = getPlayer(p2!!, players)

            if (player1 != null){
                player1.score +=  updateScore(whoWon!!, p2!!)
                if (whoWon == 1) player1.wins++
                playerModel.update(player1)
            }

            if (player2 != null){
                val item = when(whoWon!!){
                    1 -> 2
                    2 -> 1
                    else -> 0
                }
                player2.score +=  updateScore(item, p1!!)
                if (item == 1) {
                    player2.wins++
                }
                playerModel.update(player2)
            } else {
                val item = when(whoWon!!){
                    1 -> 2
                    2 -> 1
                    else -> 0
                }
                playerModel.insert(
                    Player(p2!!,
                        if (item == 1)  1 else 0,
                        updateScore(item, p1!!))
                )
            }
        }
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

    fun setText(topText: String, winnerName: String){
        top_title.text = topText
        winner_name.text = winnerName
    }

    fun updatePlayerDatabase(playerOne: String, playerTwo: String, winnerOrDraw: Int){
        p1 = playerOne
        p2 = playerTwo
        whoWon = winnerOrDraw
    }
}
