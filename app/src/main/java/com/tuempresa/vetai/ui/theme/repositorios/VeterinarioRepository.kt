package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Veterinario

class VeterinarioRepository(private val db: AppDatabase) {
    suspend fun obtenerVeterinarios() = db.veterinarioDao().obtenerVeterinarios()

    suspend fun insertar(veterinario: Veterinario) = db.veterinarioDao().insertar(veterinario)

    suspend fun actualizar(veterinario: Veterinario) = db.veterinarioDao().actualizar(veterinario)

    suspend fun eliminar(veterinario: Veterinario) = db.veterinarioDao().eliminar(veterinario)
}