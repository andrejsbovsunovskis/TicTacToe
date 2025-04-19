package com.example.tictactoe.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R
import kotlinx.android.synthetic.main.activity_player_input.*

/**
 * Activity, kurā lietotājs ievada savu vārdu
 * un izvēlas PvP vai PvC režīmu.
 */
class PlayerInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_input)

        // Poga spēles sākšanai
        btnStart.setOnClickListener {
            // Ja ievades nav, iedod noklusējuma vārdu
            val name = etPlayerName.text.toString().ifBlank { "Spēlētājs1" }
            // TODO: pievienot izvēli PvP/PvC, pašlaik vsComputer vienmēr = true
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("PLAYER_NAME", name)
                putExtra("VS_COMPUTER", true)
            }
            startActivity(intent)
        }
    }
}
