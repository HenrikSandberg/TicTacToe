package com.example.tictactoe.model.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.tictactoe.model.database.Player
import com.example.tictactoe.model.database.PlayerDAO
class PlayerRepository(private val playerDAO: PlayerDAO) {
    val allPlayersLive: LiveData<List<Player>> = playerDAO.getAllPlayersLive()

    @WorkerThread
    fun insert(player: Player){
        playerDAO.insert(player)
    }

    @WorkerThread
    fun update(player: Player){
        playerDAO.update(player)
    }

    @WorkerThread
    fun deleteAll(){
        playerDAO.deleteAll()
    }




}