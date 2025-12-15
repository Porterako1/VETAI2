package com.tuempresa.vetai

import android.content.Context
import com.tuempresa.vetai.ui.theme.AppDatabase
import com.tuempresa.vetai.ui.theme.entidades.Citas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CitasManager {

    private fun getDatabase(context: Context) = AppDatabase.getDatabase(context)

    // Obtener todas las citas
    fun obtenerCitas(context: Context, callback: (List<Citas>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val citas = getDatabase(context).citasDao().obtenerTodasLasCitas().firstOrNull() ?: emptyList()
            withContext(Dispatchers.Main) {
                callback(citas)
            }
        }
    }

    // Agregar nueva cita
    fun agregarCita(context: Context, cita: Citas, callback: (() -> Unit)? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            getDatabase(context).citasDao().insertarCita(cita)
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }

    // Eliminar cita por ID
    fun eliminarCita(context: Context, citaId: Int, callback: (() -> Unit)? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val cita = getDatabase(context).citasDao().obtenerCitaPorId(citaId)
            cita?.let {
                getDatabase(context).citasDao().eliminarCita(it)
            }
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }

    // Cancelar cita (cambiar estado)
    fun cancelarCita(context: Context, citaId: Int, callback: (() -> Unit)? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val cita = getDatabase(context).citasDao().obtenerCitaPorId(citaId)
            cita?.let {
                val citaActualizada = it.copy(estado = "Cancelada")
                getDatabase(context).citasDao().actualizarCita(citaActualizada)
            }
            withContext(Dispatchers.Main) {
                callback?.invoke()
            }
        }
    }

    // Obtener citas por estado
    fun obtenerCitasPorEstado(context: Context, estado: String, callback: (List<Citas>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val citas = getDatabase(context).citasDao().obtenerCitasPorEstado(estado).firstOrNull() ?: emptyList()
            withContext(Dispatchers.Main) {
                callback(citas)
            }
        }
    }

    // Obtener citas activas
    fun obtenerCitasActivas(context: Context, callback: (List<Citas>) -> Unit) {
        obtenerCitasPorEstado(context, "Activa", callback)
    }
}