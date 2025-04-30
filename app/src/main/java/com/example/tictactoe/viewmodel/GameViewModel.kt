package com.example.tictactoe.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Spēles stāvokļa datuklase (UI layer)
 */
data class GameUiState(
    val board: List<List<Char>> = List(3) { List(3) { ' ' } }, // 3×3 laukums
    val status: String = "Tavs gājiens",                       // ziņa lietotājam
    val current: Char = 'X',                                   // kurš tagad iet
    val finished: Boolean = false                              // spēle pabeigta?
)

/**
 * ViewModel — satur spēles loģiku un izdala UI-stāvokli caur StateFlow.
 */
class GameViewModel : ViewModel() {

    // Iekšējais stāvoklis, ko drīkst mainīt tikai ViewModel
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    /** Sāk jaunu spēli ar tukšu laukumu */
    fun startGame(playerName: String, vsComputer: Boolean) {
        _uiState.value = GameUiState(
            status = "$playerName, tavs gājiens",
            current = 'X'
        )
        // Ja vajag AI — šeit var inicializēt vsComputer karodziņu
    }

    /** Lietotāja vai AI gājiens (i, j) koordinātēs */
    fun makeMove(i: Int, j: Int) {
        val state = _uiState.value
        if (state.finished || state.board[i][j] != ' ') return   // jau aizņemts / spēle beigusies

        // atjaunojam laukumu
        val newBoard = state.board.mapIndexed { row, cols ->
            cols.mapIndexed { col, cell ->
                if (row == i && col == j) state.current else cell
            }
        }

        // vai ir uzvara?
        val winner = checkWinner(newBoard)
        val nextPlayer = if (state.current == 'X') 'O' else 'X'

        _uiState.value = state.copy(
            board = newBoard,
            status = when {
                winner != null -> "Uzvarēja $winner!"
                newBoard.all { it.none { c -> c == ' ' } } -> "Neizšķirts"
                else -> "Gājiens — $nextPlayer"
            },
            current = nextPlayer,
            finished = winner != null || newBoard.all { it.none { c -> c == ' ' } }
        )

        // Šeit var ievietot AI gājienu, ja vsComputer = true
    }

    /** Atiestata spēli uz sākumu */
    fun newGame() {
        _uiState.value = GameUiState()
    }

    /** Pārbauda, vai kāds ir uzvarējis; atgriež 'X'/'O', vai null */
    private fun checkWinner(b: List<List<Char>>): Char? {
        val lines = listOf(
            // rindas
            listOf(b[0][0], b[0][1], b[0][2]),
            listOf(b[1][0], b[1][1], b[1][2]),
            listOf(b[2][0], b[2][1], b[2][2]),
            // kolonnas
            listOf(b[0][0], b[1][0], b[2][0]),
            listOf(b[0][1], b[1][1], b[2][1]),
            listOf(b[0][2], b[1][2], b[2][2]),
            // diagonāles
            listOf(b[0][0], b[1][1], b[2][2]),
            listOf(b[0][2], b[1][1], b[2][0])
        )
        return lines.firstOrNull { it.distinct().size == 1 && it[0] != ' ' }?.first()
    }
}
