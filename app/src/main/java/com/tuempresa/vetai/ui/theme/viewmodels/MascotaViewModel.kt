package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.MutableLiveData
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import com.tuempresa.vetai.ui.theme.repositorios.MascotaRepository

class MascotaViewModel(private val repository: MascotaRepository) : BaseViewModel() {

    val listaMascotas = MutableLiveData<List<Mascota>>()

    fun cargarMascotas(idCliente: Int) = launch {
        listaMascotas.postValue(repository.obtenerMascotasDeCliente(idCliente))
    }

    fun insertar(mascota: Mascota, idCliente: Int) = launch {
        repository.insertar(mascota)
        cargarMascotas(idCliente)
    }

    fun actualizar(mascota: Mascota, idCliente: Int) = launch {
        repository.actualizar(mascota)
        cargarMascotas(idCliente)
    }

    fun eliminar(mascota: Mascota, idCliente: Int) = launch {
        repository.eliminar(mascota)
        cargarMascotas(idCliente)
    }
}
