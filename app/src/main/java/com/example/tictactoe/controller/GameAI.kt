package com.example.tictactoe.controller


class GameAI {
    fun makeMove(board: Array<Int>): Int {
        (0..1).forEach { //first looking for winning options, then looking to defend
            val opportunity =  attackOrDefend(board, if (it == 0) 1 else -1)
            if (opportunity != -1) return opportunity
        }

        if (board[4] == 0) return 4

        listOf(0,2,6,8).shuffled().forEach { item ->
            if (board[item] == 0) return item
        }

        do {
            val position = (0..8).shuffled().first()
            if (board[position] == 0) return position
        } while (true)
    }

    private fun attackOrDefend(board: Array<Int>, player: Int): Int {
        val possibleMoves = listOf(
            rowOpportunities(board.clone(), player),
            columnOpportunities(board.clone(), player),
            crossOpportunities(board.clone(), player)
        ).shuffled() // Make sure the code don't favor column, row or cross over one and other

        (0 until possibleMoves.size).forEach { item ->
            if (possibleMoves[item] != -1) return possibleMoves[item]
        }
        return -1
    }
}

private fun rowOpportunities(board: Array<Int>, player: Int): Int {
    var row = 0
    
    do {
        if ((board[row] + board[row+1] + board[row+2]) == 2 * player){
            (0..2).forEach { position ->
                if (board[row+position] == 0) return row+position
            }
        }
        row += 3
    } while (row < board.size)
    
    return -1
}

private fun columnOpportunities(board: Array<Int>, player: Int): Int {
    (0..2).forEach { column ->

        if ( (board[column] + board[column+3] + board[column+6]) == (2 * player)) {

            var item = 0
            do {
                if(board[column+item] == 0) return column+item
                item += 3
            } while (item < 9)
        }
    }
    return -1
}

private fun crossOpportunities(board: Array<Int>, player: Int): Int {
    var position = 0

    if ((board[0] + board[4] + board[8]) == (2 * player)) {

        do {
            if (board[position] == 0) return position
            position += 4
        } while (position < 9)

    } else if ((board[2] + board[4] + board[6]) == (2 * player)) {

        position = 2
        do {
            if (board[position] == 0) return position
            position += 2
        } while (position < 9)
    }
    return -1
}