package com.example.tictactoe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tictactoe.game.GameEngine
import com.example.tictactoe.player.Player

/**
 * ViewModel (UI un GameEngine)
 */
class GameViewModel : ViewModel() {
    private val engine = GameEngine()

    // Lauka stāvoklis kā LiveData, lai UI varētu uzreiz saņemt izmaiņas
    private val _board = MutableLiveData(engine.getBoard())
    val board: LiveData<Array<CharArray>> = _board

    // Statusa ziņa (kamēr gaida gājienu, uzvara, neizšķirts)
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    lateinit var player1: Player
    lateinit var player2: Player
    private var current: Player? = null

    /**
     * Sāk jaunu spēli.
     *
     * @param name1 — pirmā spēlētāja vārds
     * @param vsComputer — true PvC režīmam
     */
    fun startGame(name1: String, vsComputer: Boolean) {
        player1 = Player(name1, 'X')
        player2 = Player(
            name = if (vsComputer) "Dators" else "Spēlētājs 2",
            symbol = 'O',
            isArtificial = vsComputer
        )
        current = player1
        engine.reset()
        _board.value = engine.getBoard()
        _status.value = "${player1.name} gājiens"
    }

    /**
     * Veic gājienu un atjauno LiveData.
     */
    fun makeMove(row: Int, col: Int) {
        val sym = current!!.symbol
        if (!engine.makeMove(row, col, sym)) return

        _board.value = engine.getBoard()

        when {
            engine.checkWin(sym) -> {
                _status.value = "${current!!.name} ir uzvarējis!"
            }
            engine.isDraw() -> {
                _status.value = "Neizšķirts!"
            }
            else -> {
                // Mainām spēlētāju
                current = if (current == player1) player2 else player1
                _status.value = "Gājiens: ${current!!.name}"

                // Ja dators – liekam viņam gājienu
                if (current!!.name == "Dators") {
                    current!!.computeMove(engine.getBoard())?.let { (r, c) ->
                        makeMove(r, c)
                    }
                }
            }
        }
    }

    /** Sāk jaunu spēli no nulles. */
    fun newGame() {
        engine.reset()
        _board.value = engine.getBoard()
        current = player1
        _status.value = "Jauna spēle. Gājiens: ${player1.name}"
    }
}
