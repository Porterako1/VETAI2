package com.tuempresa.vetai

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var etUsuario: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var btnIngresar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar la barra de acción
        supportActionBar?.hide()

        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnIngresar = findViewById(R.id.btnIngresar)

        // Configurar click del botón
        btnIngresar.setOnClickListener {
            validarLogin()
        }
    }

    private fun validarLogin() {
        val usuario = etUsuario.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        when {
            usuario.isEmpty() -> {
                etUsuario.error = "Ingresa tu usuario"
                etUsuario.requestFocus()
            }
            contrasena.isEmpty() -> {
                etContrasena.error = "Ingresa tu contraseña"
                etContrasena.requestFocus()
            }
            else -> {
                // Login exitoso
                Toast.makeText(
                    this,
                    "¡Bienvenido, $usuario!",
                    Toast.LENGTH_SHORT
                ).show()

                // 🚀 NAVEGACIÓN A LA PANTALLA DE CITAS (PASO 14)
                val intent = Intent(this, CitasActivity::class.java)
                startActivity(intent)

                // Opcional: Si quieres que no pueda volver al login con el botón atrás
                // finish()
            }
        }
    }
}