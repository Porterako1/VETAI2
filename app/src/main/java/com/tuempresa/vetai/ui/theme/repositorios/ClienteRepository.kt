package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Cliente

class ClienteRepository (private val db: AppDatabase) {
    suspend fun obtenerClientes() = db.clienteDao().obtenerClientes()

    suspend fun insertar(cliente: Cliente) = db.clienteDao().insertar(cliente)

    suspend fun actualizar(cliente: Cliente) = db.clienteDao().actualizar(cliente)

    suspend fun eliminar(cliente: Cliente) = db.clienteDao().eliminar(cliente)

    suspend fun login(usuario: String, contrasena: String): Cliente? {
        return db.clienteDao().login(usuario, contrasena)
    }
    suspend fun registrar(cliente: Cliente) {
        db.clienteDao().insertar(cliente)
    }
    suspend fun existeEmail(email: String) = db.clienteDao().existeEmail(email)
}