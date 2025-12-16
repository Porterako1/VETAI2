package com.tuempresa.vetai.ui.perfil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tuempresa.vetai.databinding.ActivityMiPerfilBinding
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Cliente
import com.tuempresa.vetai.ui.theme.factory.ClienteViewModelFactory
import com.tuempresa.vetai.ui.theme.repositorios.ClienteRepository
import com.tuempresa.vetai.ui.theme.viewmodels.ClienteViewModel

class MiPerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiPerfilBinding
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var clienteActual: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ocultar ActionBar (igual que Registro)
        supportActionBar?.hide()

        // ViewModel
        val db = AppDatabase.getDatabase(this)
        val repo = ClienteRepository(db)
        val factory = ClienteViewModelFactory(repo)
        clienteViewModel = ViewModelProvider(this, factory)[ClienteViewModel::class.java]

        // Obtener sesión
        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val idCliente = prefs.getInt("id_cliente", -1)

        if (idCliente == -1) {
            finish()
            return
        }

        // Cargar datos del cliente
        cargarCliente(idCliente)

        // Guardar cambios
        binding.btnGuardar.setOnClickListener {
            actualizarPerfil()
        }

        // Volver
        binding.btnBack.setOnClickListener {
            finish()
        }

        // No permitir editar el usuario
        binding.etUsuario.isEnabled = false
    }

    private fun cargarCliente(idCliente: Int) {
        clienteViewModel.obtenerClientePorId(idCliente)
            .observe(this) { cliente ->
                clienteActual = cliente

                binding.etNombre.setText(cliente.Nombre)
                binding.etApellidos.setText(cliente.Apellidos)
                binding.etTelefono.setText(cliente.Telefono)
                binding.etUsuario.setText(cliente.Usuario)
                binding.etContrasena.setText(cliente.Contrasena)
            }
    }

    private fun actualizarPerfil() {

        val actualizado = clienteActual.copy(
            Nombre = binding.etNombre.text.toString().trim(),
            Apellidos = binding.etApellidos.text.toString().trim(),
            Telefono = binding.etTelefono.text.toString().trim(),
            Contrasena = binding.etContrasena.text.toString()
        )

        clienteViewModel.actualizar(actualizado)

        Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
    }
}

