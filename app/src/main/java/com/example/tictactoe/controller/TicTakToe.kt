package com.example.tictactoe.controller

class TicTakToe {
    private var p1:Player? = null
    private var p2:Player? = null
    private var ai:GameAI? = null

    private var oTurn = true
    private var board = arrayOf(0,0,0,0,0,0,0,0,0)


    /******************************** Public ********************************/
    constructor (player1: Player, player2: Player) { // Player vs Player
        p1 = player1
        p2 = player2
    }

    constructor (player: Player, difficulty: GameMode) { //Play vs computer
        p1 = player
        ai = GameAI(difficulty)
    }

    fun nextPlayer(): String{ return returnName(oTurn) }
    fun priviesPlayer(): String{ return returnName(!oTurn) }
    fun oJustPlayed(): Boolean { return !oTurn }
    fun noEmpty(): Boolean { return board.none { it == 0 } }

    fun makePlayerMoveIfLegal(position: Int): Boolean {
        return if (!doWeHaveAWinner() && isEmpty(position)) {
            updateGame(position)
            true
        } else false
    }

    fun makeAIMove(): Int {
        return if (ai != null && !doWeHaveAWinner() && !noEmpty()) {
            val move = ai!!.makeMove(board)
            updateGame(move)
            move
        } else -1
    }

    //TODO: Add logic so that player stats are updated here
    //TODO: If you win against easy, you will not increase your wins, however if you draw or loos, that will increase
    //TODO: Make rematch with switch of players symbol
    fun resetGame() {
        oTurn = true
        board = arrayOf(0,0,0,0,0,0,0,0,0)
    }

    fun doWeHaveAWinner(): Boolean {
        val leftToRight = board[0] + board[4] + board[8]
        val rightToLeft = board[2] + board[4] + board[6]

        if (leftToRight == -3 || leftToRight == 3 || rightToLeft == -3 || rightToLeft == 3)
            return true

        (0..2).forEach { i ->
            val valueRow = board[i*3] + board[(i*3)+1] + board[(i*3)+2]
            val valueColumn = board[i] + board[i+3] + board[i+6]

            if (valueRow == -3 || valueRow == 3 || valueColumn == -3 || valueColumn == 3)
                return true
        }
        return false
    }

    /******************************** Private ********************************/
    private fun isEmpty(position: Int): Boolean { return (board[position] == 0) }

    private fun updateGame(position: Int){
        board[position] = if (oTurn) -1 else 1
        oTurn = !oTurn
    }

    private fun returnName(bool: Boolean): String {
        return if (bool) p1.toString()
        else when (p2 != null) {
            true -> p2.toString()
            else -> ai?.getAIName() ?: "Computer"
        }
    }
}