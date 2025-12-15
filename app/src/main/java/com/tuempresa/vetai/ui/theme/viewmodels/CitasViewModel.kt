package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuempresa.vetai.ui.theme.entidades.Citas
import com.tuempresa.vetai.ui.theme.repositorios.CitasRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CitasViewModel(private val repository: CitasRepository) : ViewModel() {

    private val _listaCitas = MutableStateFlow<List<Citas>>(emptyList())
    val listaCitas: StateFlow<List<Citas>> = _listaCitas.asStateFlow()

    init {
        cargarTodasLasCitas()
    }

    private fun cargarTodasLasCitas() {
        viewModelScope.launch {
            repository.obtenerTodasLasCitas().collect { citas ->
                _listaCitas.value = citas
            }
        }
    }

    fun cargarCitasPorMascota(idMascota: Int) {
        viewModelScope.launch {
            repository.obtenerCitasPorMascota(idMascota).collect { citas ->
                _listaCitas.value = citas
            }
        }
    }

    fun insertarCita(cita: Citas) {
        viewModelScope.launch {
            repository.insertar(cita)
            cargarTodasLasCitas()
        }
    }

    fun actualizarCita(cita: Citas) {
        viewModelScope.launch {
            repository.actualizar(cita)
            cargarTodasLasCitas()
        }
    }

    fun eliminarCita(cita: Citas) {
        viewModelScope.launch {
            repository.eliminar(cita)
            cargarTodasLasCitas()
        }
    }

    fun cargarCitasPorEstado(estado: String) {
        viewModelScope.launch {
            repository.obtenerCitasPorEstado(estado).collect { citas ->
                _listaCitas.value = citas
            }
        }
    }

    fun cargarCitasPorFecha(fecha: String) {
        viewModelScope.launch {
            repository.obtenerCitasPorFecha(fecha).collect { citas ->
                _listaCitas.value = citas
            }
        }
    }
}