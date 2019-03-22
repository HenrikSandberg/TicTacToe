package com.example.tictactoe.controller

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDAO {
    @Insert fun insert(vararg player: Player)
    @Update fun update (vararg player: Player)
    @Delete fun delete(player: Player)

    @Query("DELETE FROM player_table")
    fun deleteAll()

    @Query("SELECT * FROM player_table")
    fun getAllPlayersLive() : LiveData<List<Player>>

    @Query("SELECT * FROM player_table")
    fun getAllPlayers() : List<Player>
}