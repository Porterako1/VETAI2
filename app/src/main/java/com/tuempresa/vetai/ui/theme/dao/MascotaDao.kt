package com.tuempresa.vetai.ui.theme.dao

import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Mascota
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {
    @Insert
    suspend fun insertarMascota(mascota: Mascota): Long

    @Update
    suspend fun actualizarMascota(mascota: Mascota)

    @Delete
    suspend fun eliminarMascota(mascota: Mascota)

    @Query("SELECT * FROM mascotas ORDER BY nombre ASC")
    fun obtenerTodasMascotas(): Flow<List<Mascota>>

    @Query("SELECT * FROM mascotas WHERE id = :id")
    suspend fun obtenerMascotaPorId(id: Int): Mascota?

    @Query("SELECT * FROM mascotas WHERE idCliente = :idCliente")
    fun obtenerMascotasPorCliente(idCliente: Int): Flow<List<Mascota>>
}