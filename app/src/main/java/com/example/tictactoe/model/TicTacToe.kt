package com.example.tictactoe.model

class TicTacToe {
    private var p1:String? = null
    private var p2:String? = null
    private var ai: GameAI? = null

    private var oTurn = true
    private var board = arrayOf(0,0,0,0,0,0,0,0,0)

    /******************************** Public ********************************/
    constructor (player1: String, player2: String) { // Player vs Player
        p1 = player1
        p2 = player2
    }

    constructor (player: String, difficulty: GameMode) { //Play vs computer
        p1 = player
        ai = GameAI(difficulty)
    }

    fun nextPlayer(): String = returnName(oTurn)
    fun previousPlayer(): String = returnName(!oTurn)
    fun getPlayerOne(): String = p1 ?: "No player"
    fun getPlayerTwo(): String = p2 ?: ai?.getAIName() ?: "No second player"
    fun oJustPlayed(): Boolean = !oTurn
    fun noEmpty(): Boolean = board.none { it == 0 }
    fun getGameMode(): GameMode = ai?.getGameMode() ?: GameMode.PVP

    fun makePlayerMoveIfLegal(position: Int): Boolean {
        return (
            if (!doWeHaveAWinner() && isEmpty(position)) {
                updateGame(position)
                true
            } else false
        )
    }

    fun makeAIMove(): Int {
        return if (ai != null && !doWeHaveAWinner() && !noEmpty()) {
            val move = ai!!.makeMove(board.clone())
            updateGame(move)
            move
        } else -1
    }

    fun resetGame() {
        oTurn = true
        board = arrayOf(0,0,0,0,0,0,0,0,0)
    }

    fun doWeHaveAWinner(): Boolean {
        if (checkBoard(3)) return true
        if (checkBoard(-3)) return true
        return false
    }

    fun whoWon(): Int {
        when (doWeHaveAWinner()) {
            true -> {
                if (checkBoard(-3)) return 1
                if (checkBoard(3)) return 2
            }
            false ->  if ( noEmpty() ) return 0
        }
        return 0
    }

    /******************************** Private ********************************/
    private fun isEmpty(position: Int): Boolean = board[position] == 0

    private fun updateGame(position: Int){
        board[position] = if (oTurn) -1 else 1
        oTurn = !oTurn
    }

    private fun returnName(isItOTurn: Boolean): String {
        return (
            if (isItOTurn) p1.toString()
            else when (p2 != null) {
                true -> p2.toString()
                else -> ai?.getAIName() ?: "Computer"
            }
        )
    }

    private fun checkBoard(lookingForScore: Int): Boolean {
        val leftToRight = board[0] + board[4] + board[8]
        val rightToLeft = board[2] + board[4] + board[6]

        if (leftToRight == lookingForScore || rightToLeft == lookingForScore)
            return true

        (0..2).forEach { i ->
            val valueRow = board[i*3] + board[(i*3)+1] + board[(i*3)+2]
            val valueColumn = board[i] + board[i+3] + board[i+6]

            if (valueRow == lookingForScore || valueColumn == lookingForScore)
                return true
        }
        return false
    }
}