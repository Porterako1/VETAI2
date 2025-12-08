package com.tuempresa.vetai.ui.theme.dao

import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Citas

@Dao
interface CitasDao {
    @Query("SELECT * FROM Citas WHERE ID_Mascota = :idMascota")
    suspend fun obtenerCitasDeMascota(idMascota: Int): List<Citas>

    @Insert
    suspend fun insertar(cita: Citas)

    @Update
    suspend fun actualizar(cita: Citas)

    @Delete
    suspend fun eliminar(cita: Citas)
}