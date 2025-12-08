package com.tuempresa.vetai

import com.tuempresa.vetai.ui.theme.Cita
import kotlin.collections.removeAll




import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CitasManager {

    private const val PREFS_NAME = "VetaiPrefs"
    private const val KEY_CITAS = "citas"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Obtener todas las citas
    fun obtenerCitas(context: Context): MutableList<Cita> {
        val prefs = getPreferences(context)
        val citasJson = prefs.getString(KEY_CITAS, null)

        return if (citasJson != null) {
            val type = object : TypeToken<MutableList<Cita>>() {}.type
            Gson().fromJson(citasJson, type)
        } else {
            mutableListOf()
        }
    }

    // Guardar lista de citas
    private fun guardarCitas(context: Context, citas: MutableList<Cita>) {
        val prefs = getPreferences(context)
        val citasJson = Gson().toJson(citas)
        prefs.edit().putString(KEY_CITAS, citasJson).apply()
    }

    // Agregar nueva cita
    fun agregarCita(context: Context, cita: Cita) {
        val citas = obtenerCitas(context)
        citas.add(cita)
        guardarCitas(context, citas)
    }

    // Eliminar cita por ID
    fun eliminarCita(context: Context, citaId: String) {
        val citas = obtenerCitas(context)
        citas.removeAll { it.id == citaId }
        guardarCitas(context, citas)
    }

    // Cancelar cita (cambiar estado)
    fun cancelarCita(context: Context, citaId: String) {
        val citas = obtenerCitas(context)
        val cita = citas.find { it.id == citaId }
        cita?.estado = "Cancelada"
        guardarCitas(context, citas)
    }

    // Obtener citas activas (no canceladas)
    fun obtenerCitasActivas(context: Context): List<Cita> {
        return obtenerCitas(context).filter { it.estado != "Cancelada" }
    }
}