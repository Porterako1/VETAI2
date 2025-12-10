package com.tuempresa.vetai.ui.theme.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Mascota

@Dao
interface MascotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(mascota: Mascota): Long

    @Update
    suspend fun actualizar(mascota: Mascota)

    @Delete
    suspend fun eliminar(mascota: Mascota)

    @Query("SELECT * FROM mascotas WHERE clienteId = :clienteId ORDER BY fechaRegistro DESC")
    fun obtenerMascotasPorCliente(clienteId: Int): LiveData<List<Mascota>>

    @Query("SELECT * FROM mascotas WHERE id = :id")
    fun obtenerMascotaPorId(id: Int): LiveData<Mascota>

    @Query("SELECT * FROM mascotas ORDER BY fechaRegistro DESC")
    fun obtenerTodasLasMascotas(): LiveData<List<Mascota>>

    @Query("DELETE FROM mascotas WHERE clienteId = :clienteId")
    suspend fun eliminarMascotasDeCliente(clienteId: Int)
}