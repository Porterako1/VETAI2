package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.MutableLiveData
import com.tuempresa.vetai.ui.theme.entidades.Veterinario
import com.tuempresa.vetai.ui.theme.repositorios.VeterinarioRepository

class VeterinarioViewModel(private val repository: VeterinarioRepository) : BaseViewModel() {

    val listaVeterinarios = MutableLiveData<List<Veterinario>>()

    fun cargarVeterinarios() = launch {
        listaVeterinarios.postValue(repository.obtenerVeterinarios())
    }

    fun insertar(v: Veterinario) = launch {
        repository.insertar(v)
        cargarVeterinarios()
    }

    fun actualizar(v: Veterinario) = launch {
        repository.actualizar(v)
        cargarVeterinarios()
    }

    fun eliminar(v: Veterinario) = launch {
        repository.eliminar(v)
        cargarVeterinarios()
    }
}
