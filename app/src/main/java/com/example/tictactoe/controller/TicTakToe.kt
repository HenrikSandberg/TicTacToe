package com.example.tictactoe.controller

class TicTakToe{
    private var firstPlayersTurn = true
    private var game = arrayOf(0,0,0,0,0,0,0,0,0)


    /********************************Public Functions********************************/
    fun lookAtBoard(): Array<Int> { return game }

    fun makeMove(position: Int): Boolean {
        if (!haveAWinner()) {
            if (isEmpty(position)) {
                game[position] = if (firstPlayersTurn) -1 else 1
                firstPlayersTurn = !firstPlayersTurn
                return true
            }
        }
        return false
    }

    fun isItFirstTurn(): Boolean { return firstPlayersTurn }

    fun resetGame() {
        firstPlayersTurn = true
        game = arrayOf(0,0,0,0,0,0,0,0,0)
    }

    fun noEmpty(): Boolean {
        (0 until game.size).forEach {position  ->
            if (game[position] == 0)  return false
        }
        return true
    }

    fun haveAWinner(): Boolean { return (lookCross() || lookColumn() || lookRow()) }

    /********************************Private Functions********************************/
    private fun isEmpty(position: Int): Boolean { return (game[position] == 0) }

    private fun lookRow(): Boolean {
        var i = 0
        while (i < game.size) {
            val value = game[i] + game[i+1] + game[i+2]
            if (value == -3 || value == 3) return true
            i += 3
        }
        return false
    }

    private fun lookColumn(): Boolean {
        (0..2).forEach { column ->
            val value = game[column] + game[column+3] + game[column+6]
            if (value == -3 || value == 3) return true
        }
        return false
    }

    private fun lookCross(): Boolean {
        val leftToRight = game[0] + game[4] + game[8]
        val rightToLeft = game[2] + game[4] + game[6]
        return (leftToRight == -3 || leftToRight == 3 || rightToLeft == -3 || rightToLeft == 3)
    }
}