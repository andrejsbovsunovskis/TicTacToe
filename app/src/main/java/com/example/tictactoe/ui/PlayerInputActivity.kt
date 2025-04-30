package com.example.tictactoe.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tictactoe.ui.theme.TicTacToeTheme

/**
 * Spēlētāja ievades Activity — ļauj ievadīt vārdu,
 * izvēlēties spēles režīmu un palaist spēli.
 */
class PlayerInputActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ielādē Compose koku ar mūsu tēmu
        setContent {
            TicTacToeTheme {
                PlayerInputScreen { playerName, vsComputer ->
                    // Pāriet uz GameActivity ar nodotiem parametriem
                    startActivity(
                        Intent(this, GameActivity::class.java).apply {
                            putExtra("PLAYER_NAME", playerName)
                            putExtra("VS_COMPUTER", vsComputer)
                        }
                    )
                }
            }
        }
    }
}

/* -------------------------------------------------------------------------- */
/* ---------------------------  Compose ekrāns  ----------------------------- */
/* -------------------------------------------------------------------------- */

/**
 * Ekrāns lietotāja vārdam un režīma izvēlei.
 *
 * @param onStartGame Callback, kas saņem (name, vsComputer) un startē spēli.
 */
@Composable
fun PlayerInputScreen(
    onStartGame: (String, Boolean) -> Unit
) {
    // Lokālais stāvoklis (Compose remember)
    var name by remember { mutableStateOf("") }
    var vsComputer by remember { mutableStateOf(true) }

    // Sakārtojam elementus kolonnā
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* ---------------------  Vārda ievades lauks  -------------------- */
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Spēlētāja vārds") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            /* --------------------  Režīma pārslēdzējs  ---------------------- */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Pret datoru")
                Switch(
                    checked = vsComputer,
                    onCheckedChange = { vsComputer = it }
                )
            }

            /* -----------------------  Poga «Start»  ------------------------ */
            Button(
                onClick = { if (name.isNotBlank()) onStartGame(name, vsComputer) },
                enabled = name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sākt spēli")
            }
        }
    }
}
