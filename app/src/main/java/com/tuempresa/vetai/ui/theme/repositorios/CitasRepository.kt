package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Citas
import com.tuempresa.vetai.ui.theme.dao.CitasDao

class CitasRepository(private val db: AppDatabase) {
    suspend fun obtenerCitasDeMascota(idMascota: Int) = db.citasDao().obtenerCitasDeMascota(idMascota)

    suspend fun insertar(cita: Citas) = db.citasDao().insertar(cita)

    suspend fun actualizar(cita: Citas) = db.citasDao().actualizar(cita)

    suspend fun eliminar(cita: Citas) = db.citasDao().eliminar(cita)
}