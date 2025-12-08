package com.tuempresa.vetai.ui.theme.dao

import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Mascota

@Dao
interface MascotaDao {
    @Query("SELECT * FROM Mascota WHERE ID_Cliente = :idCliente")
    suspend fun obtenerMascotasDeCliente(idCliente: Int): List<Mascota>

    @Insert
    suspend fun insertar(mascota: Mascota)

    @Update
    suspend fun actualizar(mascota: Mascota)

    @Delete
    suspend fun eliminar(mascota: Mascota)
}