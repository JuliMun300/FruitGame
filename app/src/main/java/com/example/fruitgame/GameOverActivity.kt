package com.example.fruitgame

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private lateinit var TextViewPuntos: TextView
private lateinit var TextViewHighScore: TextView
private lateinit var BotonBack:ImageButton
private var PuntosGanados = 0

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        TextViewPuntos = findViewById(R.id.textViewpuntos)
        TextViewHighScore = findViewById(R.id.textViewHighScore)
        BotonBack = findViewById(R.id.btnback)

        val PuntosGuardados = PrefsHelper.prefs.GetPoints()
        TextViewHighScore.text = "High Score: $PuntosGuardados"

        val bundle = intent.extras
        if (bundle != null) {
            PuntosGanados = bundle.getInt("PuntosGanados")
            TextViewPuntos.text = PuntosGanados.toString()
        }

        BotonBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}