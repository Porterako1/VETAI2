package com.tuempresa.vetai.ui.theme.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tuempresa.vetai.ui.theme.entidades.Cliente
import com.tuempresa.vetai.ui.theme.repositorios.ClienteRepository
import kotlinx.coroutines.launch

class ClienteViewModel(private val repository: ClienteRepository) : BaseViewModel() {

    val listaClientes = MutableLiveData<List<Cliente>>()

    fun cargarClientes() = launch {
        listaClientes.postValue(repository.obtenerClientes())
    }

    fun insertar(cliente: Cliente) = launch {
        repository.insertar(cliente)
        cargarClientes()
    }

    fun actualizar(cliente: Cliente) = launch {
        repository.actualizar(cliente)
        cargarClientes()
    }

    fun eliminar(cliente: Cliente) = launch {
        repository.eliminar(cliente)
        cargarClientes()
    }
    fun login(usuario: String, contrasena: String): LiveData<Cliente?> {
        val resultado = MutableLiveData<Cliente?>()

        viewModelScope.launch {
            val cliente = repository.login(usuario, contrasena)
            resultado.postValue(cliente)
        }

        return resultado
    }

    fun registrar(cliente: Cliente) {
        viewModelScope.launch {
            repository.registrar(cliente)
        }
    }

    fun obtenerClientes(): LiveData<List<Cliente>> {
        val lista = MutableLiveData<List<Cliente>>()

        viewModelScope.launch {
            lista.postValue(repository.obtenerClientes())
        }

        return lista
    }
    fun existeEmail(email: String) = liveData {
        emit(repository.existeEmail(email))
    }

}
