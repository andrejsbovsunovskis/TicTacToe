package com.example.tictactoe.player

/**
 * Spēlētāja datu klase.
 *
 * @param name — spēlētāja vārds
 * @param symbol — 'X' vai 'O'
 */
data class Player(val name: String, val symbol: Char) {
    /**
     * Vienkāršs AI: atgriež pirmo brīvo šūnu.
     *
     * @param board — pašreizējais lauka stāvoklis
     * @return koordinātes vai null, ja brīvu šūnu nav
     */
    fun computeMove(board: Array<CharArray>): Pair<Int, Int>? {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == ' ') return i to j
            }
        }
        return null
    }
}
