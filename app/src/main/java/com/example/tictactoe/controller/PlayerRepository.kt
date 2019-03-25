package com.example.tictactoe.controller

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class PlayerRepository(private val playerDAO: PlayerDAO) {
    val allPlayersLive: LiveData<List<Player>> = playerDAO.getAllPlayersLive()

    @WorkerThread
    suspend fun insert(player: Player){
        playerDAO.insert(player)
    }

    @WorkerThread
    suspend fun update(player: Player){
        playerDAO.update(player)
    }

    @WorkerThread
    suspend fun deleteAll(){
        playerDAO.deleteAll()
    }




}