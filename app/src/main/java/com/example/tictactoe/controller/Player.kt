package com.example.tictactoe.controller

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player (
    @PrimaryKey @ColumnInfo (name = "name")
    val player:String,

    @ColumnInfo (name = "wins")
    var wins:Int,

    @ColumnInfo (name = "score")
    var score:Int
)