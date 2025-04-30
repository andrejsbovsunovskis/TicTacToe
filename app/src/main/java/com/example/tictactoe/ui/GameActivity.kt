package com.example.tictactoe.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tictactoe.viewmodel.GameViewModel
import com.example.tictactoe.ui.theme.TicTacToeTheme

/**
 * Galvenā spēles Activity — izvada Compose ekrānu,
 * saņem spēlētāja / AI gājienus un reaģē uz stāvokļa izmaiņām.
 */
class GameActivity : ComponentActivity() {

    // ViewModel instance saņemšana
    private val vm: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nolasa parametrus no intent
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Spēlētājs"
        val vsComputer = intent.getBooleanExtra("VS_COMPUTER", true)

        // Iniciē spēli ar lietotāja vārdu un režīmu
        vm.startGame(playerName, vsComputer)

        // Izsauc Compose koku
        setContent {
            TicTacToeTheme {
                GameScreen(vm = vm)
            }
        }
    }
}

/* -------------------------------------------------------------------------- */
/* ---------------------------  Compose komponents  ------------------------- */
/* -------------------------------------------------------------------------- */

/**
 * Saknes ekrāns — satur režģi, statusa joslu un pogu “Jauna spēle”.
 */
@Composable
fun GameScreen(vm: GameViewModel) {
    val uiState by vm.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Board(
                board = uiState.board,
                onCellClick = { i, j -> vm.makeMove(i, j) }
            )
            StatusBar(text = uiState.status)
            NewGameButton(onClick = vm::newGame)
        }
    }
}

/**
 * 3×3 režģis. Katru šūnu ataino kā Text.
 */
@Composable
fun Board(
    board: List<List<Char>>,
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        board.forEachIndexed { i, row ->
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                row.forEachIndexed { j, symbol ->
                    Cell(
                        symbol = symbol.takeIf { it != ' ' }?.toString() ?: "",
                        onClick = { onCellClick(i, j) }
                    )
                }
            }
        }
    }
}

/**
 * Viena šūna ar simbolu — klikšķināma.
 */
@Composable
fun Cell(symbol: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(96.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        tonalElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = symbol,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

/**
 * Statusa josla — rāda, kurš gājiens / uzvara / neizšķirts.
 */
@Composable
fun StatusBar(text: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.bodyLarge
    )
}

/**
 * Poga “Jauna spēle”.
 */
@Composable
fun NewGameButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Jauna spēle")
    }
}
