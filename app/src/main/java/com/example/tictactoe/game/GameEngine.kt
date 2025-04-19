package com.example.tictactoe.game

/**
    Glabā 3×3 spēles laukumu un atbild par gājieniem,
    uzvaras un neizšķirta pārbaudi.
 */

class GameEngine {

    // ' ' — tukša šūna, 'X' vai 'O' — aizpildīta
    private val board = Array(3) { CharArray(3) { ' ' } }

    /** Veic gājienu */
    fun makeMove(row: Int, col: Int, symbol: Char): Boolean {
        if (board[row][col] != ' ') return false
        board[row][col] = symbol
        return true
    }

    /** Pārbauda uzvaru */
    fun checkWin(symbol: Char): Boolean {

        for (i in 0..2) {
            if (board[i][0] == symbol &&
                board[i][1] == symbol &&
                board[i][2] == symbol
            ) return true
        }

        for (j in 0..2) {
            if (board[0][j] == symbol &&
                board[1][j] == symbol &&
                board[2][j] == symbol
            ) return true
        }

        if (board[0][0] == symbol &&
            board[1][1] == symbol &&
            board[2][2] == symbol
        ) return true

        if (board[0][2] == symbol &&
            board[1][1] == symbol &&
            board[2][0] == symbol
        ) return true

        return false
    }

    /** Pārbauda, vai laukums pilnībā aizpildīts */
    fun isDraw(): Boolean {
        return board.all { row -> row.all { it != ' ' } }
    }

    /** Iztukšo laukumu */
    fun reset() {
        for (i in 0..2) for (j in 0..2) board[i][j] = ' '
    }

    /** Atgriež pašreizējo laukuma stāvokli UI attēlošanai */
    fun getBoard(): Array<CharArray> = board
}
