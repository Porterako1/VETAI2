package com.tuempresa.vetai

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class InicioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Ocultar la barra de acción si existe
        supportActionBar?.hide()

        // Obtener referencias a las tarjetas
        val cardMisMascotas = findViewById<CardView>(R.id.cardMisMascotas)
        val cardCitas = findViewById<CardView>(R.id.cardCitas)
        val cardMiPerfil = findViewById<CardView>(R.id.cardMiPerfil)

        // Click en "Mis Mascotas" - Abre la lista de mascotas
        cardMisMascotas.setOnClickListener {
            val intent = Intent(this, ListaMascotasActivity::class.java)
            startActivity(intent)
        }

        // Click en "Mis Citas" - Abre el menú de citas ✅ CORREGIDO
        cardCitas.setOnClickListener {
            val intent = Intent(this, CitasActivity::class.java)
            startActivity(intent)
        }

        // Click en "Mi Perfil" - Puedes agregar tu activity de perfil aquí
        cardMiPerfil.setOnClickListener {
            // TODO: Crear PerfilActivity si lo necesitas
        }
    }
}