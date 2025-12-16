package com.tuempresa.vetai.ui.theme.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Cliente

@Dao
interface ClienteDao {
    @Query("SELECT * FROM Cliente")
    suspend fun obtenerClientes(): List<Cliente>

    @Query("SELECT * FROM Cliente WHERE Usuario = :usuario AND Contrasena = :contrasena LIMIT 1")
    suspend fun login(usuario: String, contrasena: String): Cliente?

    @Query("SELECT * FROM Cliente WHERE Usuario = :email LIMIT 1")
    suspend fun existeEmail(email: String): Cliente?
    @Query("SELECT * FROM Cliente WHERE ID_Cliente = :id LIMIT 1")
    fun obtenerClientePorId(id: Int): LiveData<Cliente>
    @Insert
    suspend fun insertar(cliente: Cliente)

    @Update
    suspend fun actualizar(cliente: Cliente)

    @Delete
    suspend fun eliminar(cliente: Cliente)
}