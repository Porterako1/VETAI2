package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "citas",
    foreignKeys = [
        ForeignKey(
            entity = Mascota::class,
            parentColumns = ["id"],
            childColumns = ["idMascota"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Veterinario::class,
            parentColumns = ["ID_Veterinario"],
            childColumns = ["idVeterinario"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["idMascota"]),
        Index(value = ["idVeterinario"])
    ]
)
data class Citas(
    @PrimaryKey(autoGenerate = true)
    val ID_Cita: Int = 0,
    val idMascota: Int = 1,
    val idVeterinario: Int? = 1,  // Nullable porque tiene SET_NULL
    val fecha: String,
    val hora: String,
    val motivo: String,
    var estado: String = "Activa"
)