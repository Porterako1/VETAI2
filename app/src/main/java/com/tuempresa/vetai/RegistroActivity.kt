package com.tuempresa.vetai



import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.tuempresa.vetai.ui.theme.Usuario
import com.tuempresa.vetai.ui.theme.UsuariosManager

class RegistroActivity : AppCompatActivity() {


    private lateinit var btnBack: ImageView
    private lateinit var etNombre: TextInputEditText
    private lateinit var etApellidos: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etContrasenaReg: TextInputEditText
    private lateinit var etConfirmar: TextInputEditText
    private lateinit var btnRegistrarse: Button
    private lateinit var tvYaTienesCuenta: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Ocultar la barra de acción
        supportActionBar?.hide()

        // Inicializar vistas
        btnBack = findViewById(R.id.btnBack)
        etNombre = findViewById(R.id.etNombre)
        etApellidos = findViewById(R.id.etApellidos)
        etEmail = findViewById(R.id.etEmail)
        etContrasenaReg = findViewById(R.id.etContrasenaReg)
        etConfirmar = findViewById(R.id.etConfirmar)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        tvYaTienesCuenta = findViewById(R.id.tvYaTienesCuenta)

        // Configurar eventos
        btnBack.setOnClickListener {
            finish()
        }

        btnRegistrarse.setOnClickListener {
            registrarUsuario()
        }

        tvYaTienesCuenta.setOnClickListener {
            finish() // Volver al login
        }
    }

    private fun registrarUsuario() {
        val nombre = etNombre.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val contrasena = etContrasenaReg.text.toString()
        val confirmar = etConfirmar.text.toString()

        // Validaciones
        when {
            nombre.isEmpty() -> {
                etNombre.error = "Ingresa tu nombre"
                etNombre.requestFocus()
                return
            }

            apellidos.isEmpty() -> {
                etApellidos.error = "Ingresa tus apellidos"
                etApellidos.requestFocus()
                return
            }

            email.isEmpty() -> {
                etEmail.error = "Ingresa tu correo electrónico"
                etEmail.requestFocus()
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Ingresa un correo válido"
                etEmail.requestFocus()
                return
            }

            contrasena.isEmpty() -> {
                etContrasenaReg.error = "Ingresa una contraseña"
                etContrasenaReg.requestFocus()
                return
            }

            contrasena.length < 6 -> {
                etContrasenaReg.error = "La contraseña debe tener al menos 6 caracteres"
                etContrasenaReg.requestFocus()
                return
            }

            confirmar.isEmpty() -> {
                etConfirmar.error = "Confirma tu contraseña"
                etConfirmar.requestFocus()
                return
            }

            contrasena != confirmar -> {
                etConfirmar.error = "Las contraseñas no coinciden"
                etConfirmar.requestFocus()
                return
            }
        }
        // Crear objeto Usuario
        val nuevoUsuario = Usuario(
            nombre = nombre,
            apellidos = apellidos,
            email = email,
            contrasena = contrasena
        )

        // Intentar registrar
        val registroExitoso = UsuariosManager.registrarUsuario(this, nuevoUsuario)

        if (registroExitoso) {
            Toast.makeText(
                this,
                "¡Cuenta creada exitosamente! Ya puedes iniciar sesión",
                Toast.LENGTH_LONG
            ).show()
            finish() // Volver al login
        } else {
            Toast.makeText(
                this,
                "Este correo ya está registrado",
                Toast.LENGTH_SHORT
            ).show()
            etEmail.error = "Correo ya registrado"
            etEmail.requestFocus()
        }
    }
}