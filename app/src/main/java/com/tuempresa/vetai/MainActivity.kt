package com.tuempresa.vetai

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.factory.ClienteViewModelFactory
import com.tuempresa.vetai.ui.theme.repositorios.ClienteRepository
import com.tuempresa.vetai.ui.theme.viewmodels.ClienteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var etUsuario: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var btnIngresar: MaterialButton
    private lateinit var tvRegistro: TextView

    private lateinit var clienteViewModel: ClienteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ocultar la barra de acción
        supportActionBar?.hide()

        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnIngresar = findViewById(R.id.btnIngresar)
        tvRegistro = findViewById(R.id.tvRegistro)

        // Inicializar BD / Repo / ViewModel
        val db = AppDatabase.getDatabase(this)
        val repo = ClienteRepository(db)
        val factory = ClienteViewModelFactory(repo)
        clienteViewModel = ViewModelProvider(this, factory)[ClienteViewModel::class.java]

        // Configurar click del botón
        btnIngresar.setOnClickListener {
            validarLogin()
        }
        tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
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
                clienteViewModel.login(usuario, contrasena).observe(this) { user ->
                    if (user != null) {
                        // Guardar ID del cliente en SharedPreferences
                        guardarSesion(user.ID_Cliente, user.Nombre, user.Usuario)

                        Toast.makeText(this, "¡Bienvenido, ${user.Nombre}!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun guardarSesion(clienteId: Int, nombreCliente: String, usuario: String) {
        val prefs = getSharedPreferences("VetaiPrefs", MODE_PRIVATE)
        prefs.edit().apply {
            putInt("cliente_id", clienteId)
            putString("cliente_nombre", nombreCliente)
            putBoolean("sesion_activa", true)
            apply()
        }
    }
}


