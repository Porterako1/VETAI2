package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.*

@Entity(
    tableName = "Tratamiento",
    foreignKeys = [
        ForeignKey(
            entity = Mascota::class,
            parentColumns = ["id"],
            childColumns = ["ID_Mascota"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Citas::class,
            parentColumns = ["ID_Cita"],
            childColumns = ["ID_Cita"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ID_Mascota"), Index("ID_Cita")]
)

data class Tratamiento(
    @PrimaryKey(autoGenerate = true)
    val ID_Tratamiento: Int = 0,
    val ID_Mascota: Int,
    val ID_Cita: Int,
    val Fecha: String,
    val Descripcion: String
)