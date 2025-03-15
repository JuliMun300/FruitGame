package com.example.fruitgame

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private lateinit var BotonJugar: Button
private lateinit var BotonInstrucciones :Button
private lateinit var ImagenFruta: ImageView
val FrutasNombres = mutableListOf("fresa", "mango", "manzana", "naranja", "sandia", "uva")
val puntosreinicio = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BotonJugar = findViewById(R.id.Botonjugar)
        ImagenFruta = findViewById(R.id.imagen_principal)
        BotonInstrucciones = findViewById(R.id.botonInstrucciones)
        val NumeroAleatorio = generarNumeroAleatorio()
        EstablecerImagen(NumeroAleatorio)
        ChangeStatusBarColor()

        BotonJugar.setOnClickListener {
            val intent = Intent(this, JugarActivity::class.java)
            intent.putExtra("ReinicioPuntos", puntosreinicio)
            startActivity(intent)
            finish()

        }
        BotonInstrucciones.setOnClickListener {
            Toast.makeText(this, "WORK IN PROGRESS", Toast.LENGTH_SHORT).show()
        }
    }

    fun ChangeStatusBarColor() {
        //Para cambiar el color del Status Bar
        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.teal_200)
        }
    }

    private fun EstablecerImagen(Numero: Int) {
        val resId = resources.getIdentifier(FrutasNombres[Numero], "drawable", packageName)
        ImagenFruta.setImageResource(resId)

    }

    fun generarNumeroAleatorio(): Int {
        val numero = (0 until FrutasNombres.size).random()
        return numero
    }
}