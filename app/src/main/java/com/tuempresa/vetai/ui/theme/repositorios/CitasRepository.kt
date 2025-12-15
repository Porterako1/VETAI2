package com.tuempresa.vetai.ui.theme.repositorios

import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Citas
import kotlinx.coroutines.flow.Flow

class CitasRepository(private val db: AppDatabase) {

    suspend fun obtenerCitasPorMascota(idMascota: Int): Flow<List<Citas>> =
        db.citasDao().obtenerCitasPorMascota(idMascota)

    suspend fun insertar(cita: Citas) = db.citasDao().insertarCita(cita)

    suspend fun actualizar(cita: Citas) = db.citasDao().actualizarCita(cita)

    suspend fun eliminar(cita: Citas) = db.citasDao().eliminarCita(cita)

    fun obtenerTodasLasCitas(): Flow<List<Citas>> =
        db.citasDao().obtenerTodasLasCitas()

    fun obtenerCitasPorEstado(estado: String): Flow<List<Citas>> =
        db.citasDao().obtenerCitasPorEstado(estado)

    fun obtenerCitasPorFecha(fecha: String): Flow<List<Citas>> =
        db.citasDao().obtenerCitasPorFecha(fecha)

    suspend fun obtenerCitaPorId(id: Int): Citas? =
        db.citasDao().obtenerCitaPorId(id)
}