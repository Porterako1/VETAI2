package com.tuempresa.vetai.ui.theme



import java.io.Serializable

data class Cita(
    val id: String = System.currentTimeMillis().toString(),
    val fecha: String,
    val hora: String,
    val motivo: String,
    val mascota: String = "Mi Mascota", // Por ahora fijo, luego se integrará
    var estado: String = "Confirmada" // Confirmada, Cancelada
) : Serializable