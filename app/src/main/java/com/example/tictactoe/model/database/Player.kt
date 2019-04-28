package com.example.tictactoe.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_table")
data class Player (
    @PrimaryKey @ColumnInfo (name = "name")
    val name:String,

    @ColumnInfo (name = "wins")
    var wins:Int,

    @ColumnInfo (name = "score")
    var score:Int
)