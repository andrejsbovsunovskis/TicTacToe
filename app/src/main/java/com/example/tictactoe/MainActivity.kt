package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

import com.example.tictactoe.ui.PlayerInputActivity

/**
 * Ieejas aktivitāte. Uzreiz pārsūta lietotāju uz PlayerInputActivity,
 * lai viņš nonāktu izvēles ekrānā, un pēc tam pati aizveras, lai
 * nepaliktu “Back stack-ā”.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Palaižam ekrānu ar spēlētāju izvēli
        startActivity(Intent(this, PlayerInputActivity::class.java))

        // Aizveram šo aktivitāti, lai pēc “Back” nepārādītu tukšu ekrānu
        finish()
    }
}
