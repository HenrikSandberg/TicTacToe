package com.example.tictactoe.controller

class Player(_name: String) {
    private val name = _name
    private var numberOfWins = 0
    private var numberOfLoos = 0
    private var numberOfDraw = 0

    override fun toString(): String {
        return name
    }

}