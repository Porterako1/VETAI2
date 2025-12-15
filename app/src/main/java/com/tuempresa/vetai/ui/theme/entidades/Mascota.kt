package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mascotas")
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: Int,
    val peso: Double,
    val sexo: String,
    val idCliente: Int = 0,
    val observaciones: String = ""
)