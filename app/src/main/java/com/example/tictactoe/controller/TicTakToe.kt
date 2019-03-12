package com.example.tictactoe.controller

class TicTakToe{
    private var firstPlayersTurn = true
    private var game = arrayOf(
        arrayOf(0,0,0),
        arrayOf(0,0,0),
        arrayOf(0,0,0)
    )

    fun setBrick(row: Int, column: Int): Boolean {
        if (isEmpty(row, column)) {
            game[row][column] = if (firstPlayersTurn) 10 else 2
            firstPlayersTurn = !firstPlayersTurn
            return true
        }
        return false
    }

    fun isItFirstTurn(): Boolean {return firstPlayersTurn}

    fun resetGame() {
        firstPlayersTurn = true
        game = arrayOf(
            arrayOf(0,0,0),
            arrayOf(0,0,0),
            arrayOf(0,0,0)
        )
    }

    fun isEmpty(row: Int, column: Int): Boolean { return (game[row][column] == 0) }

    fun noEmpty(): Boolean{
        (0..game.size).forEach { row ->
            (0..game[row].size).forEach { column ->
                if (game[row][column] == 0)  return false
            }
        }
        return true
    }

    private fun lookRow(): Boolean{
        (0..game.size).forEach { row ->
            val value = game[row][0] + game[row][1] + game[row][2]
            if (value == 30 || value == 6) return true
        }
        return false
    }

    private fun lookColumn(): Boolean {
        (0..game.size).forEach { column ->
            val value = game[0][column] + game[1][column] + game[2][column]
            if (value == 30 || value == 6) return true
        }
        return false
    }

    private fun lookCross(): Boolean{
        val leftToRight = game[0][0] + game[1][1] + game[2][2]
        val rightToLeft = game[2][0] + game[1][1] + game[0][2]
        return (leftToRight == 30 || leftToRight == 6 || rightToLeft == 30 || rightToLeft == 6)
    }

    fun haveAWinner(): Boolean { return (lookCross() || lookColumn() || lookRow())}
}