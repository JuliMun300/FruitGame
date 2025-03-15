package com.example.fruitgame

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private lateinit var ImagenFruta: ImageView
private lateinit var TextviewPuntos: TextView
private lateinit var TextViewTiempo: TextView
private lateinit var musica_fondo: MediaPlayer
private lateinit var sonido_mordisco:MediaPlayer
private var puntos = 0
private var TiempoInicial: Long = 6000
private var tiempoactual: Long = 0
private lateinit var context: Context
private lateinit var contador: CountDownTimer


class JugarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugar)
        ImagenFruta = findViewById(R.id.imagen_fruta_jugar)
        TextviewPuntos = findViewById(R.id.textViewpuntos)
        ImagenFruta.setImageResource(R.drawable.fresa)
        TextViewTiempo = findViewById(R.id.textViewtiempo)
        context = this
        ChangeStatusBarColor()

        musica_fondo = MediaPlayer.create(this,R.raw.musica_fondo)
        sonido_mordisco = MediaPlayer.create(this,R.raw.sonido_mordisco)

        musica_fondo.start()

        Contador(TiempoInicial)
        ImagenFruta.setOnClickListener {
            val numero = generarNumeroAleatorios()
            ChangePosition()
            ChangeFruit(numero)
            ChangeScore()

        }
    }

    //funcion para el contador
    private fun Contador(tiempo: Long) {
        contador = object : CountDownTimer(tiempo, 1000) {
            override fun onTick(tiemposegundos: Long) {
                tiempoactual = tiemposegundos
                TextViewTiempo.text = (tiemposegundos / 1000).toString()
            }

            override fun onFinish() {
                TextViewTiempo.text = "Fin"
                val intent = Intent(context, GameOverActivity::class.java)
                intent.putExtra("PuntosGanados", puntos)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                CalculatePoints(puntos)
                Restart()
                musica_fondo.stop()
                musica_fondo.release()
                startActivity(intent)
                finish()
            }

        }.start()
    }

    //Funcion para resetear las variables
    private fun Restart() {
        TiempoInicial = 6000
        val PuntosReinicidados = intent.extras?.getInt("ReinicioPuntos")
        if (PuntosReinicidados != null) {
            puntos = PuntosReinicidados
        }
    }

    //Funcion para calcular los puntos
    private fun CalculatePoints(puntos: Int) {
        val puntosalmacenados = PrefsHelper.prefs.GetPoints()

        if (puntos > puntosalmacenados) {
            PrefsHelper.prefs.SavePoints(puntos)
        }
    }

    //Funcion para generar un numero aletaroio
    fun generarNumeroAleatorios(): Int {
        val numero = (0 until FrutasNombres.size).random()
        return numero
    }

    //Funcion para cuando los puntos sean multiplos de 5 y sumar 1 segundo
    fun ChangeScore() {
        puntos++
        if (puntos % 5 == 0) {
            contador.cancel()
            TiempoInicial = 0
            TiempoInicial = tiempoactual + 1000
            Contador(TiempoInicial)
        }
        TextviewPuntos.text = "Puntos: $puntos"
    }

    //Funcion para cambiar una fruta cada vez que se presione una
    private fun ChangeFruit(numero: Int) {
        val resId = resources.getIdentifier(FrutasNombres[numero], "drawable", packageName)
        ImagenFruta.setImageResource(resId)
    }

    //Funcion para cambiar la posicion de la fruta cada vez que se presione
    private fun ChangePosition() {

        sonido_mordisco.start()
        if(sonido_mordisco.isPlaying){
            sonido_mordisco.seekTo(0)
        }

        val randomX = (0..500).random().toFloat()
        val randomY = (0..1000).random().toFloat()

        val layoutParams = ImagenFruta.layoutParams as? ViewGroup.MarginLayoutParams
        layoutParams?.leftMargin = randomX.toInt()
        layoutParams?.topMargin = randomY.toInt()
        ImagenFruta.layoutParams = layoutParams
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

    override fun onDestroy() {
        super.onDestroy()
        
    }
}
