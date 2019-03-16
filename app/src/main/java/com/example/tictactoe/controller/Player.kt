package com.example.tictactoe.controller

//TODO make as entity in database.
class Player(name: String) {
    private val player = name
    private var numberOfWins = 0
    private var numberOfLoos = 0
    private var numberOfDraw = 0

    override fun toString(): String { return player }
}