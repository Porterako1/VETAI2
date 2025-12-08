package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.tuempresa.vetai.ui.theme.entidades.Citas
import com.tuempresa.vetai.ui.theme.repositorios.CitasRepository

class CitasViewModel(private val repository: CitasRepository) : BaseViewModel() {

    val listaCitas = MutableLiveData<List<Citas>>()

    fun cargarCitas(idMascota: Int) = launch {
        listaCitas.postValue(repository.obtenerCitasDeMascota(idMascota))
    }

    fun insertar(cita: Citas) = launch {
        repository.insertar(cita)
        cargarCitas(cita.ID_Mascota)
    }

    fun actualizar(cita: Citas) = launch {
        repository.actualizar(cita)
        cargarCitas(cita.ID_Mascota)
    }

    fun eliminar(cita: Citas) = launch {
        repository.eliminar(cita)
        cargarCitas(cita.ID_Mascota)
    }

}
