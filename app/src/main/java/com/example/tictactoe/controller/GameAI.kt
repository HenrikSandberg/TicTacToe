package com.example.tictactoe.controller

enum class GameMode {
    EASY,
    HARD,
    IMPOSSIBLE
}

class GameAI(_gameMode: GameMode) {
    private val gameMode = _gameMode

    /******************************** PUBLIC ********************************/
    fun getAIName(): String {
        return when (gameMode) {
            GameMode.IMPOSSIBLE -> "Deep Thought"
            GameMode.HARD -> "Marvin"
            else -> "Eddie the Computer"
        }
    }

    fun makeMove(board: Array<Int>): Int {
        if (gameMode == GameMode.IMPOSSIBLE) {
            if (!validityOfBoard(board)) {
                return minMax(board, 8, 1)[1] //minMaxReturns an array, at index 0 is the predicted outcome of move
            }
        } else if (gameMode == GameMode.HARD) {
            (0..1).forEach { player ->
                //First looking for winning options, then looking to defend. This is represented with 1 and -1
                val opportunity = attackOrDefend(board, if (player == 0) 1 else -1)
                if (opportunity != -1) return opportunity
            }

            listOf(0, 2, 4, 6, 8)
                .shuffled()
                .forEach { item -> if (board[item] == 0) return item }
        }
        return pickEmptySpot(board)
    }

    /******************************** PRIVATE ********************************/
    /******************************** EASY ********************************/
    private fun pickEmptySpot(board: Array<Int>): Int {
        return (0..8)
            .shuffled()
            .first { item -> board[item] == 0 }
    }

    /******************************** HARD ********************************/
    private fun attackOrDefend(board: Array<Int>, player: Int): Int {
        listOf( // Make sure the code don't favor column, row or cross over one and other
            rowOpportunities(board, player),
            columnOpportunities(board, player),
            crossOpportunities(board, player)
        ).shuffled()
            .forEach { item -> if (item != -1) return item }
        return -1
    }

    private fun rowOpportunities(board: Array<Int>, player: Int): Int {
        var row = 0
        do {
            if ((board[row] + board[row + 1] + board[row + 2]) == 2 * player) {
                (0..2).forEach { position ->
                    if (board[row + position] == 0)
                        return row + position
                }
            }
            row += 3
        } while (row < board.size)
        return -1
    }

    private fun columnOpportunities(board: Array<Int>, player: Int): Int {
        (0..2).forEach { column ->
            if ((board[column] + board[column + 3] + board[column + 6]) == (2 * player)) {
                var position = 0
                do {
                    if (board[column + position] == 0) return column + position
                    position += 3
                } while (position < 9)
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

    /******************************** ADVANCED ********************************/
    private fun minMax(board: Array<Int>, depth: Int, player: Int): Array<Int> {
        val possibleMoveList = possibleMoves(board)

        var bestScore = if (player == 1) Int.MIN_VALUE else Int.MAX_VALUE

        var currentScore: Int
        var bestMove = -1

        if (possibleMoveList.isEmpty() || depth == 0)
            return arrayOf(setScore(board), bestMove)

        possibleMoveList.forEach { position ->
            board[position] = player

            currentScore = minMax(board, depth - 1, -player)[0]

            if (if (player == 1) (currentScore > bestScore) else (currentScore < bestScore)) {
                bestScore = currentScore
                bestMove = position
            }

            board[position] = 0
        }

        return arrayOf(bestScore, bestMove)
    }

    private fun possibleMoves(board: Array<Int>): MutableList<Int> {
        val moves: MutableList<Int> = mutableListOf()

        if (doWeHaveAWinner(board)) return moves

        (0 until board.size) //Makes a list of all empty spaces on the board
            .filter { board[it] == 0 }
            .forEach { moves.add(it) }
        return moves
    }

    private fun doWeHaveAWinner(board: Array<Int>): Boolean {
        val leftToRight = board[0] + board[4] + board[8]
        val rightToLeft = board[2] + board[4] + board[6]

        if (leftToRight == -3 || leftToRight == 3 || rightToLeft == -3 || rightToLeft == 3)
            return true

        (0..2).forEach { i ->
            val valueRow = board[i * 3] + board[(i * 3) + 1] + board[(i * 3) + 2]
            val valueColumn = board[i] + board[i + 3] + board[i + 6]

            if (valueRow == -3 || valueRow == 3 || valueColumn == -3 || valueColumn == 3)
                return true
        }
        return false
    }

    private fun setScore(board: Array<Int>): Int { //Calculates value of board when no moves are left.
        var score = 0

        (0..2).forEach { position ->
            score += lineScore(
                board[position * 3],
                board[(position * 3) + 1],
                board[(position * 3) + 2]
            ) // calculate all row values

            score += lineScore(
                board[position],
                board[position + 3],
                board[position + 6]
            ) //calculate all column values
        }

        //Calculate the diagonal values
        score += lineScore(board[0], board[4], board[8])
        score += lineScore(board[2], board[4], board[6])
        return score
    }

    //Returns zero when there is no opportunities otherwise it returns a positive value, the greater the better
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
/******************************** END ********************************/





































/******************************** Eh.... Way are you here ********************************/







    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    






/******************************** You should have stopped scrolling by now ********************************/





























































/******************************** Way do you insist on scrolling ********************************/








































































/******************************** OK, you can stop scrolling now ********************************/


















































/******************************** .... ********************************/














    
    
    
    
































    
    












/******************************** Fin... ********************************/
/******************************** This is how you can stop my unstoppable AI ********************************/
/******************************** Turns out that it's not that fun to loose against a computer ********************************/
/******************************** Again and again... and again ********************************/
/******************************** So I made a easter egg ********************************/
/******************************** If both you and the universe pick 42 ********************************/
/******************************** and by universe I mean Kotlin .shuffled() ********************************/
/******************************** The AI turns dum for one move! ********************************/
/******************************** It's still improbable that you will win, but it is possible ********************************/
private fun validityOfBoard(board: Array<Int>): Boolean {
    if (board[4] == -1 && board[2] == -1 ) {
        listOf(0,1,3,5,6,7,8).forEach{
            if (board[it] == -1) return false
        }
        if ((1..1000).shuffled().first() == 42) return true
    }
    return false
}