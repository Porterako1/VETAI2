package com.tuempresa.vetai.ui.theme.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import com.tuempresa.vetai.ui.theme.repositorios.MascotaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MascotaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MascotaRepository

    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas: StateFlow<List<Mascota>> = _mascotas.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    init {
        val mascotaDao = AppDatabase.getDatabase(application).mascotaDao()
        repository = MascotaRepository(mascotaDao)

        viewModelScope.launch {
            repository.todasLasMascotas.collect { listaMascotas ->
                _mascotas.value = listaMascotas
            }
        }
    }

    fun registrarMascota(
        nombre: String,
        especie: String,
        raza: String,
        edad: String,
        peso: String,
        sexo: String,
        observaciones: String
    ) {
        viewModelScope.launch {
            try {
                if (nombre.isBlank() || especie.isBlank()) {
                    _mensaje.value = "El nombre y la especie son obligatorios"
                    return@launch
                }

                val edadInt = edad.toIntOrNull() ?: 0
                val pesoDouble = peso.toDoubleOrNull() ?: 0.0

                val nuevaMascota = Mascota(
                    nombre = nombre.trim(),
                    especie = especie.trim(),
                    raza = raza.trim(),
                    edad = edadInt,
                    peso = pesoDouble,
                    sexo = sexo,
                    observaciones = observaciones.trim()
                )

                repository.insertarMascota(nuevaMascota)
                _mensaje.value = "Mascota registrada exitosamente"
            } catch (e: Exception) {
                _mensaje.value = "Error al registrar: ${e.message}"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    fun eliminarMascota(mascota: Mascota) {
        viewModelScope.launch {
            try {
                repository.eliminarMascota(mascota)
            } catch (e: Exception) {
                _mensaje.value = "Error al eliminar: ${e.message}"
            }
        }
    }
}