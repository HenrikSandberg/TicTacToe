package com.example.tictactoe.controller

class TicTakToe{
    enum class GameMode { PVP, EASY, HARD, IMPOSSIBLE }
    private var p1:Player? = null
    private var p2:Player? = null

    private var gameMode:GameMode? = null
    private var ai:GameAI? = null

    private var firstPlayersTurn = true
    private var board = arrayOf(0,0,0,0,0,0,0,0,0)

    constructor(_player1: Player, _player2: Player) {
        p1 = _player1
        p2 = _player2
        gameMode = GameMode.PVP
    }

    constructor(_player: Player, _gameMode: GameMode) {
        p1 = _player
        gameMode = _gameMode
        ai = if (gameMode != GameMode.PVP) GameAI(gameMode!!) else null
    }


    /********************************Public Functions********************************/
    fun nextPlayer(): String{
        if (firstPlayersTurn) return p1.toString()
        return when(gameMode) {
            GameMode.PVP -> p2!!.toString()
            else -> ai?.aiName() ?: "Computer"
        }
    }

    fun priviesPlayer(): String{
        return if (gameMode == GameMode.PVP) {
            if (!firstPlayersTurn) p1.toString() else p2!!.toString()
        } else {
            if (!firstPlayersTurn) p1.toString() else ai?.aiName() ?: "Computer"
        }
    }

    fun makePlayerMoveIfLegal(position: Int): Boolean {
        return if (!haveAWinner() && isEmpty(position)) {
            updateGame(position)
            true
        } else false
    }

    fun makeAIMove(): Int {
        return if (ai != null && !haveAWinner() && !noEmpty()) {
            val move = ai!!.makeMove(board)
            updateGame(move)
            move
        } else -1
    }

    fun oPlayed(): Boolean { return !firstPlayersTurn }

    //TODO: Add logic so that player stats are updated here
    //TODO: Make rematch with switch of players symbol
    fun resetGame() {
        firstPlayersTurn = true
        board = arrayOf(0,0,0,0,0,0,0,0,0)
    }

    fun noEmpty(): Boolean { return board.none { it == 0 } }

    fun haveAWinner(): Boolean {
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

    /********************************Private Functions********************************/

    private fun updateGame(position: Int){
        board[position] = if (firstPlayersTurn) -1 else 1
        firstPlayersTurn = !firstPlayersTurn
    }

    private fun isEmpty(position: Int): Boolean { return (board[position] == 0) }

}