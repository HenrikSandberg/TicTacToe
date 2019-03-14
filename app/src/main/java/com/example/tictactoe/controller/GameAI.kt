package com.example.tictactoe.controller


class GameAI {
    fun makeMove(board: Array<Int>): Int{
        (0..1).forEach {
            val opportunity =  attackOrDefend(board, if (it == 0) 1 else -1)
            if (opportunity != -1) return opportunity
        }

        if (board[4] == 0) return 4

        listOf(0,2,6,8).shuffled().forEach { item ->
            if (board[item] == 0) return item
        }

        while (true){
            val position = (0..8).shuffled().first()
            if (board[position] == 0) return position
        }
    }

    private fun attackOrDefend(board: Array<Int>, player: Int): Int{
        var row = 0
        while (row < board.size) {
            val value = board[row] + board[row+1] + board[row+2]
            if (value == 2 * player){
                (0..2).forEach {position ->
                    if (board[row+position] == 0) return row+position
                }
            }
            row += 3
        }
        (0..2).forEach { column ->
            val value = board[column] + board[column+3] + board[column+6]
            if (value == 2 * player) {
                var i = 0
                while (i < 9){
                    if(board[column+i] == 0) return column+i
                    i += 3
                }
            }
        }
        val leftToRight = board[0] + board[4] + board[8]
        val rightToLeft = board[2] + board[4] + board[6]
        if (leftToRight == 2 * player) {
            var i = 0
            while (i < 9) {
                if (board[i] == 0) return i
                i += 4
            }
        } else if (rightToLeft == 2 * player) {
            var i = 2
            while (i < 9){
                if(board[i] == 0) return i
                i += 2
            }
        }
        return -1
    }
}