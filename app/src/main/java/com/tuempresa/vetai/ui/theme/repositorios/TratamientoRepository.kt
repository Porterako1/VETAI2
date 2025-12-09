package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Tratamiento

class TratamientoRepository(private val db: AppDatabase) {
    suspend fun obtenerTratamientosDeMascota(idMascota: Int) = db.tratamientoDao().obtenerTratamientosDeMascota(idMascota)

    suspend fun insertar(tratamiento: Tratamiento) = db.tratamientoDao().insertar(tratamiento)

    suspend fun actualizar(tratamiento: Tratamiento) = db.tratamientoDao().actualizar(tratamiento)

    suspend fun eliminar(tratamiento: Tratamiento) = db.tratamientoDao().eliminar(tratamiento)
}