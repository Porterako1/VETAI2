package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import com.tuempresa.vetai.ui.theme.repositorios.MascotaRepository
import kotlinx.coroutines.launch

class MascotaViewModel(private val repository: MascotaRepository) : ViewModel() {

    // Lista observable de mascotas
    private val _listaMascotas = MutableLiveData<List<Mascota>>()
    val listaMascotas: LiveData<List<Mascota>> = _listaMascotas

    // Para manejar errores
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // Para manejar operaciones exitosas
    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

    // Cargar mascotas de un cliente
    fun cargarMascotas(idCliente: Int) {
        viewModelScope.launch {
            try {
                val mascotas = repository.obtenerMascotasPorCliente(idCliente)
                _listaMascotas.value = mascotas as List<Mascota>?
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar mascotas: ${e.message}"
            }
        }
    }

    // Insertar una nueva mascota
    fun insertar(mascota: Mascota, idCliente: Int) {
        viewModelScope.launch {
            try {
                repository.insertar(mascota)
                _successMessage.value = "Mascota registrada exitosamente"
                cargarMascotas(idCliente) // Recargar la lista
            } catch (e: Exception) {
                _errorMessage.value = "Error al registrar mascota: ${e.message}"
            }
        }
    }

    // Insertar con callback (para compatibilidad con tu código actual)
    fun insertar(mascota: Mascota, idCliente: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.insertar(mascota)
                _successMessage.value = "Mascota registrada exitosamente"
                cargarMascotas(idCliente)
                callback(true)
            } catch (e: Exception) {
                _errorMessage.value = "Error al registrar mascota: ${e.message}"
                callback(false)
            }
        }
    }

    // Actualizar mascota existente
    fun actualizar(mascota: Mascota, idCliente: Int) {
        viewModelScope.launch {
            try {
                repository.actualizar(mascota)
                _successMessage.value = "Mascota actualizada exitosamente"
                cargarMascotas(idCliente)
            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar mascota: ${e.message}"
            }
        }
    }

    // Eliminar mascota
    fun eliminar(mascota: Mascota, idCliente: Int) {
        viewModelScope.launch {
            try {
                repository.eliminar(mascota)
                _successMessage.value = "Mascota eliminada exitosamente"
                cargarMascotas(idCliente)
            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar mascota: ${e.message}"
            }
        }
    }

    // Obtener una mascota por ID
    fun obtenerMascotaPorId(id: Int): Mascota? {
        return _listaMascotas.value?.find { it.id == id }
    }
}