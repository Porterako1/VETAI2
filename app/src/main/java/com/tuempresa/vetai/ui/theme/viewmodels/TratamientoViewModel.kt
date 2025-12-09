package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.MutableLiveData
import com.tuempresa.vetai.ui.theme.entidades.Tratamiento
import com.tuempresa.vetai.ui.theme.repositorios.TratamientoRepository

class TratamientoViewModel(private val repository: TratamientoRepository) : BaseViewModel() {

    val listaTratamientos = MutableLiveData<List<Tratamiento>>()

    fun cargarTratamientos(idMascota: Int) = launch {
        listaTratamientos.postValue(repository.obtenerTratamientosDeMascota(idMascota))
    }

    fun insertar(t: Tratamiento) = launch {
        repository.insertar(t)
        cargarTratamientos(t.ID_Mascota)
    }

    fun actualizar(t: Tratamiento) = launch {
        repository.actualizar(t)
        cargarTratamientos(t.ID_Mascota)
    }

    fun eliminar(t: Tratamiento) = launch {
        repository.eliminar(t)
        cargarTratamientos(t.ID_Mascota)
    }
}
