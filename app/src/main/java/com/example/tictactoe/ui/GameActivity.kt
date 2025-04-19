package com.example.tictactoe.ui

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R
import com.example.tictactoe.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.activity_game.*

/**
 * Galvenā spēles Activity — attēlo 3×3 režģi,
 * saņem spēlētāja un AI gājienus un atjauno statusu.
 */
class GameActivity : AppCompatActivity() {

    // ViewModel instances saņemšana
    private val vm: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Nolasa parametrus no intent
        val playerName = intent.getStringExtra("PLAYER_NAME")!!
        val vsComputer = intent.getBooleanExtra("VS_COMPUTER", true)

        // Iniciē spēli ar lietotāja vārdu un režīmu
        vm.startGame(playerName, vsComputer)

        // Veido 3×3 šūnu režģi
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val cell = layoutInflater
                    .inflate(R.layout.item_cell, gridBoard, false) as TextView
                cell.setOnClickListener {
                    vm.makeMove(i, j)
                }
                gridBoard.addView(cell)
            }
        }

        // Saņem izmaiņas un atjauno šūnas
        vm.board.observe(this) { board ->
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    val idx = i * 3 + j
                    (gridBoard.getChildAt(idx) as TextView).text =
                        board[i][j].toString().takeIf { it != " " } ?: ""
                }
            }
        }

        // Saņem statusa ziņas (gājiens, uzvara, neizšķirts)
        vm.status.observe(this) { tvStatus.text = it }

        // Poga jaunai spēlei
        btnNewGame.setOnClickListener {
            vm.newGame()
        }
    }
}
