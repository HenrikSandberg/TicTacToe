package com.example.tictactoe.controller

class GameAI {
    /********************************Public Functions********************************/
    fun makeMove(board: Array<Int>, hardMode: Boolean): Int {
        if (hardMode){
            return minMax(board, 7,1)[1]
        } else {

            //First looking for winning options, then looking to defend. This is represented with 1 and -1
            (0..1).forEach {
                val opportunity =  attackOrDefend(board, if (it == 0) 1 else -1)
                if (opportunity != -1) return opportunity
            }

            listOf(0,2,6,8,4).shuffled().forEach { item ->
                if (board[item] == 0) return item
            }

            do {
                val position = (0..8).shuffled().first()
                if (board[position] == 0) return position
            } while (true)
        }
    }

    /********************************Simple AI********************************/
    private fun attackOrDefend(board: Array<Int>, player: Int): Int {
        val possibleMoves = listOf(
            rowOpportunities(board, player),
            columnOpportunities(board, player),
            crossOpportunities(board, player)
        ).shuffled() // Make sure the code don't favor column, row or cross over one and other

        (0 until possibleMoves.size).forEach { item ->
            if (possibleMoves[item] != -1) return possibleMoves[item]
        }
        return -1
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

    /********************************Advanced AI********************************/
    private fun minMax(board: Array<Int>, depth: Int, player: Int): Array<Int> {
        val possibleMoveList = possibleMoves(board)

        var bestScore = if (player == 1) Int.MIN_VALUE else Int.MAX_VALUE

        var currentScore: Int
        var bestMove= -1

        if (possibleMoveList.isEmpty() || depth == 0)  return arrayOf( setScore(board), bestMove)

        possibleMoveList.forEach {position ->
            board[position] = player

            currentScore = minMax(board, depth - 1, -player)[0]

            if ( if (player == 1) (currentScore > bestScore) else (currentScore < bestScore)) {
                bestScore = currentScore
                bestMove = position
            }

            board[position] = 0
        }

        return arrayOf(bestScore, bestMove)
    }

    private fun possibleMoves(board: Array<Int>): MutableList<Int> {
        val moves: MutableList<Int> = mutableListOf()

        if (haveAWinner(board)) { return moves }

        ( 0 until board.size) //Makes a list of all empty spaces on the board
            .filter { board[it] == 0}
            .forEach { moves.add(it) }

        return moves
    }

    private fun haveAWinner(board: Array<Int>): Boolean { //Same method as in TicTakToe game, but condensed down to one method
        val leftToRight = board[0] + board[4] + board[8]
        val rightToLeft = board[2] + board[4] + board[6]

        if (leftToRight == -3 || leftToRight == 3 || rightToLeft == -3 || rightToLeft == 3) return true

        (0..2).forEach { i ->
            val valueRow = board[i*3] + board[(i*3)+1] + board[(i*3)+2]
            val valueColumn = board[i] + board[i+3] + board[i+6]

            if (valueRow == -3 || valueRow == 3 || valueColumn == -3 || valueColumn == 3) return true
        }
        return false
    }

    private fun setScore(board: Array<Int>): Int { //Calculates ultimate value when no moves are left
        var score = 0

        (0..2).forEach { position ->
            score += lineScore(board[position * 3], board[(position * 3) + 1], board[(position * 3) + 2]) // calculate all row values
            score += lineScore(board[position], board[position + 3], board[position + 6]) //calculate all column values
        }

        //Calculate the diagonal values
        score += lineScore(board[0], board[4], board[8])
        score += lineScore(board[2], board[4], board[6])
        return score
    }

    //Returns zero when there is no opportunities otherwise returns a negative score if loosing or a positive score if winning
    private fun lineScore(firstItem: Int, secondItem: Int, thirdItem: Int): Int {
        var score = firstItem

        if (secondItem == 1)
            score = when (score) {
                1 -> 10
                -1 -> return 0
                else -> 1
            }
        else if (secondItem == -1)
            score = when (score) {
                -1 -> -10
                1 -> return 0
                else -> -1
            }

        if (thirdItem == 1)
            when {
                score > 0 -> score *= 10
                score < 0 -> return 0
                else -> score = 1
            }
        else if (thirdItem == -1)
            when {
                score < 0 -> score *= 10
                score > 1 -> return 0
                else -> score = -1
            }
        return score
    }
}