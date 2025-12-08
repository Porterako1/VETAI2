package com.tuempresa.vetai.ui.theme.dao

import androidx.room.*
import com.tuempresa.vetai.ui.theme.entidades.Veterinario

@Dao
interface VeterinarioDao {
    @Query("SELECT * FROM Veterinario")
    suspend fun obtenerVeterinarios(): List<Veterinario>

    @Insert
    suspend fun insertar(veterinario: Veterinario)

    @Update
    suspend fun actualizar(veterinario: Veterinario)

    @Delete
    suspend fun eliminar(veterinario: Veterinario)
}