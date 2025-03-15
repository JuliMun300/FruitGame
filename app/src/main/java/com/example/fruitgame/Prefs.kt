package com.example.fruitgame

import android.content.Context

class Prefs(val context: Context) {
    private val SHARED_PREFS = "PuntosDatos"
    private val SHARED_NAME = "PuntosGuardados"

    val sharedPref = context.getSharedPreferences(SHARED_PREFS, 0)

    fun SavePoints(points: Int) {
        sharedPref.edit().putInt(SHARED_NAME, points).apply()
    }

    fun GetPoints(): Int {
        return sharedPref.getInt(SHARED_NAME, 0)
    }
}