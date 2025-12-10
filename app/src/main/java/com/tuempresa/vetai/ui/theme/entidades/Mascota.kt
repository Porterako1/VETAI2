package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "mascotas",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["ID_Cliente"],
            childColumns = ["clienteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val raza: String,
    val edad: Int, // en años
    val peso: Double, // en kilogramos
    val color: String,
    val sexo: String, // "Macho" o "Hembra"
    val especie: String, // "Perro", "Gato", etc.
    val esterilizado: Boolean = false,
    val clienteId: Int, // Relación con el cliente dueño
    val fechaRegistro: Long = System.currentTimeMillis()
)