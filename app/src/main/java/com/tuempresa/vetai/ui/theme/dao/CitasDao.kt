package com.tuempresa.vetai.ui.theme.dao

import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Citas  
import kotlinx.coroutines.flow.Flow

@Dao
interface CitasDao {

    @Insert
    suspend fun insertarCita(cita: Citas): Long

    @Update
    suspend fun actualizarCita(cita: Citas)

    @Delete
    suspend fun eliminarCita(cita: Citas)

    @Query("SELECT * FROM citas ORDER BY fecha DESC, hora DESC")
    fun obtenerTodasLasCitas(): Flow<List<Citas>>

    @Query("SELECT * FROM citas WHERE ID_Cita = :id")
    suspend fun obtenerCitaPorId(id: Int): Citas?

    @Query("SELECT * FROM citas WHERE idMascota = :idMascota")
    fun obtenerCitasPorMascota(idMascota: Int): Flow<List<Citas>>

    @Query("SELECT * FROM citas WHERE estado = :estado")
    fun obtenerCitasPorEstado(estado: String): Flow<List<Citas>>

    @Query("SELECT * FROM citas WHERE fecha = :fecha")
    fun obtenerCitasPorFecha(fecha: String): Flow<List<Citas>>
}