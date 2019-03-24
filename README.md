# TicTacToe
Code structure are as follows. Firstly it is split in to two subfolders the first folder contains logic that does not present to screen. This is the *Controller* folder. It now contains two types of controller data. The first is the game logic and the game AI, the other part of this folder is the logic for the SQLite structure using the room framework. These two parts will later get there own subfolders.

The other folder in the app structure is the **model** folder. It contains all the Kotlin files that control over the UI. There is only one Activity file in the structure. All other files are Fragments.

## MainActivity
The main activity fil is the only file in the structure. It contains a fragment manager and a set of methods in order to handle all changes to the fragments represented to the end user. 

## Game AI
This file contains tree public methods and a hole bunch of private methods. The most noteworthy is the 

```kt
private fun minMax(board: Array<Int>, depth: Int, player: Int): Array<Int>
```

This method returns an array with two items. The fist is the score on how good a given move is, and the second is the actual move. By using recursion this method will find the best possible move up to nine moves in to the future. 

