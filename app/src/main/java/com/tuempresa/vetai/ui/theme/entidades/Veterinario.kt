package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Veterinario")
data class Veterinario(
    @PrimaryKey(autoGenerate = true)
    val ID_Veterinario: Int = 0,
    val Nombre: String,
    val Especialidad: String,
    val Telefono: String,
    val Mail: String
)
