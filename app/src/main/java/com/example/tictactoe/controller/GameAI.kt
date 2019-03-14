package com.example.tictactoe.controller

class GameAI{
    private var move = -1
    private var winner = 0

    fun makeMove(board: Array<Int>): Int{
        if (miniMax(board, 1) >= 0){
            return move
        }
        return 8
    }
    private fun miniMax(board: Array<Int>, player: Int): Int{
        if (haveAWinner(board)){  return winner * player  }
        move = -1
        var score = -2
        (0 until board.size).forEach { i ->
            if (board[i] == 0) {
                board[i] = player
                val newPlayer= -player
                val scoreForMove = (miniMax(board.clone(), newPlayer))
                if (scoreForMove > score) {
                    score = scoreForMove
                    move = i
                }
            }
        }
        if (move == -1){
            return 0
        }
        return score
    }

    private fun lookRow(board: Array<Int>): Boolean {
        var i = 0
        while (i < board.size) {
            val value = board[i] + board[i+1] + board[i+2]
            if (value == -3 || value == 3) {
                winner = if (value == -3) -1 else 1
                return true
            }
            i += 3
        }
        return false
    }

    private fun lookColumn(board: Array<Int>): Boolean {
        (0..2).forEach { column ->
            val value = board[column] + board[column+3] + board[column+6]
            if (value == -3 || value == 3) {
                winner = if (value == -3) -1 else 1
                return true
            }
        }
        return false
    }

    private fun lookCross(board: Array<Int>): Boolean {
        val leftToRight = board[0] + board[4] + board[8]
        val rightToLeft = board[2] + board[4] + board[6]
        if (leftToRight == -3 || leftToRight == 3){
            winner = if (leftToRight == -3) -1 else 1
            return true
        }else if (rightToLeft == -3 || rightToLeft == 3){
            winner = if (rightToLeft == -3) -1 else 1
            return true
        }
        return false
    }

    private fun haveAWinner(board: Array<Int>): Boolean { return (lookCross(board) || lookColumn(board) || lookRow(board)) }
}