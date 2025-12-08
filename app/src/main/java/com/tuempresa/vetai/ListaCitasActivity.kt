package com.tuempresa.vetai

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuempresa.vetai.ui.theme.Cita
import com.tuempresa.vetai.CitasAdapter

class ListaCitasActivity : AppCompatActivity() {

    private lateinit var rvCitas: RecyclerView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var citasAdapter: CitasAdapter
    private var modo: String = "VER" // VER o CANCELAR

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_citas)

        modo = intent.getStringExtra("MODO") ?: "VER"

        // Configurar toolbar
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Inicializar vistas
        rvCitas = findViewById(R.id.rvCitas)
        layoutEmpty = findViewById(R.id.layoutEmpty)

        // Configurar RecyclerView
        rvCitas.layoutManager = LinearLayoutManager(this)

        cargarCitas()
    }

    override fun onResume() {
        super.onResume()
        cargarCitas()
    }

    private fun cargarCitas() {
        val citas = if (modo == "CANCELAR") {
            CitasManager.obtenerCitasActivas(this).toMutableList()
        } else {
            CitasManager.obtenerCitas(this)
        }

        if (citas.isEmpty()) {
            rvCitas.visibility = View.GONE
            layoutEmpty.visibility = View.VISIBLE
        } else {
            rvCitas.visibility = View.VISIBLE
            layoutEmpty.visibility = View.GONE

            citasAdapter = CitasAdapter(citas, modo) { cita ->
                mostrarDialogEliminar(cita)
            }
            rvCitas.adapter = citasAdapter
        }
    }

    private fun mostrarDialogEliminar(cita: Cita) {
        val mensaje = if (modo == "CANCELAR") {
            "¿Estás seguro de que deseas cancelar esta cita?"
        } else {
            "¿Estás seguro de que deseas eliminar esta cita?"
        }

        AlertDialog.Builder(this)
            .setTitle(if (modo == "CANCELAR") "Cancelar Cita" else "Eliminar Cita")
            .setMessage(mensaje)
            .setPositiveButton("Sí") { _, _ ->
                if (modo == "CANCELAR") {
                    CitasManager.cancelarCita(this, cita.id)
                } else {
                    CitasManager.eliminarCita(this, cita.id)
                }
                cargarCitas()
            }
            .setNegativeButton("No", null)
            .show()
    }
}