package com.tuempresa.vetai.ui.theme.entidades

import androidx.room.*

@Entity(
    tableName = "Mascota",
    foreignKeys = [
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["ID_Cliente"],
            childColumns = ["ID_Cliente"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("ID_Cliente")]
)
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val ID_Mascota: Int = 0,
    val ID_Cliente: Int,
    val Nombre: String,
    val Especie: String,
    val Raza: String,
    val Edad: Int,
    val Sexo: String
)
