package com.tuempresa.vetai

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class InicioActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val cardMisMascotas = findViewById<CardView>(R.id.cardMisMascotas)
        val cardCitas = findViewById<CardView>(R.id.cardCitas)
        val cardMiPerfil = findViewById<CardView>(R.id.cardMiPerfil)

        // Navegacion a actividad mis mascotas
        cardMisMascotas.setOnClickListener {

            startActivity(Intent(this, RegistroMascotaActivity::class.java))
        }

        cardCitas.setOnClickListener {
            startActivity(Intent(this, CitasActivity::class.java))
        }

        // Navegacion a actividad mi perfil
        cardMiPerfil.setOnClickListener {
            //startActivity(Intent(this, PerfilActivity::class.java))
        }
    }
}