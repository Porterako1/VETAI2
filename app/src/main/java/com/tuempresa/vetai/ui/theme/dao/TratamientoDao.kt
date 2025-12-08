package com.tuempresa.vetai.ui.theme.dao

import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Tratamiento

@Dao
interface TratamientoDao {
    @Query("SELECT * FROM Tratamiento WHERE ID_Mascota = :idMascota")
    suspend fun obtenerTratamientosDeMascota(idMascota: Int): List<Tratamiento>

    @Insert
    suspend fun insertar(tratamiento: Tratamiento)

    @Update
    suspend fun actualizar(tratamiento: Tratamiento)

    @Delete
    suspend fun eliminar(tratamiento: Tratamiento)
}