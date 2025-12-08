package com.tuempresa.vetai

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class CitasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        // Configurar botón volver
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Configurar card Nueva Cita
        val cardNuevaCita = findViewById<CardView>(R.id.cardNuevaCita)
        cardNuevaCita.setOnClickListener {
            mostrarDialogNuevaCita()
        }

        // Configurar card Ver Citas
        val cardVerCitas = findViewById<CardView>(R.id.cardVerCitas)
        cardVerCitas.setOnClickListener {
            val intent = Intent(this, ListaCitasActivity::class.java)
            intent.putExtra("MODO", "VER")
            startActivity(intent)
        }

        // Configurar card Cancelar Cita
        val cardCancelarCita = findViewById<CardView>(R.id.cardCancelarCita)
        cardCancelarCita.setOnClickListener {
            val intent = Intent(this, ListaCitasActivity::class.java)
            intent.putExtra("MODO", "CANCELAR")
            startActivity(intent)
        }
    }

    private fun mostrarDialogNuevaCita() {
        val dialog = NuevaCitaDialog()
        dialog.show(supportFragmentManager, "NuevaCitaDialog")
    }
}