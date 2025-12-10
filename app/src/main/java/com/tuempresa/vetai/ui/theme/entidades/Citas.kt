package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.*

@Entity(
    tableName = "Citas",
    foreignKeys = [
        ForeignKey(
            entity = Mascota::class,
            parentColumns = ["id"],
            childColumns = ["ID_Mascota"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Veterinario::class,
            parentColumns = ["ID_Veterinario"],
            childColumns = ["ID_Veterinario"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ID_Mascota"), Index("ID_Veterinario")]
)

data class Citas(
    @PrimaryKey(autoGenerate = true)
    val ID_Cita: Int = 0,
    val ID_Mascota: Int,
    val ID_Veterinario: Int,
    val Fecha: String,
    val Hora: String,
    val Motivo: String,
    val Estado: String
)
